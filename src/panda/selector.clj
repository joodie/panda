(ns panda.selector
  "## Implementations for the selector and finder protocols.")

(declare selector)

(defprotocol TreeFinderPattern
  "A pattern that can used to construct a finder. A finder will select a MapEntry from a tree."
  (finder [pattern]
    "Construct a tree finder out of pattern.

A finder will select a single MapEntry from a tree or return nil if
there is no match.

The default finder constructed from pattern `x` will `find` `x` in
the given tree.

A pattern that is a function is considered a finder.

Sequential patterns generate a finder that finds the head of the pattern,
where the last entry of the pattern may be a tree.

See also `panda.core/find-branch` and `panda.core/get-branch`."))

(extend-protocol TreeFinderPattern
  clojure.lang.Fn
  (finder [f]
    f)

  Object
  (finder [pattern]
    (fn [tree]
      (find tree pattern)))

  clojure.lang.Sequential
  (finder [[head & rest]]
    (if rest
        (let [find-head (finder head)
              find-rest (finder rest)]
          (fn [tree]
            (if-let [entry (find-head tree)]
              (assoc entry 1 (find-rest (val entry))))))
        (selector head)))) ; last element may match a tree

(defprotocol TreeSelectorPattern
  "A pattern that can be used to construct a selector. A selector will select a subtree from a tree."
  (selector [pattern]
    "Construct a tree selector out of pattern.

A selector will return new tree with one or more branches from a given
tree or return nil if no branch matches.

The default selector constructed from pattern `x` is based on `(finder
x)`.

A pattern that is a function is considered a selector.

A pattern that is a set will match the branches of the tree that mach
the patterns in the set, based on `(finder item)` for each item in the
set.

  Ex. ((selector #{:branch1
                   [:branch2 :level2 :level3 #{:leaf1 :leaf2}]
                   [:branch3 #{:leaf-a :leaf-b}]}) tree)

See also `panda.core/select-tree`"))

(extend-protocol TreeSelectorPattern
  clojure.lang.Fn
  (selector [f]
    f)
  
  Object
  (selector [pattern]
    (let [f (finder pattern)]
      (fn [tree]
        (if-let [entry (f tree)]
          (conj (empty tree) entry)))))
  
  clojure.lang.IPersistentSet
  (selector [pattern]
    (let [finders (apply juxt (map finder pattern))]
      (fn [tree]
        (if-let [entries (seq (remove nil? (finders tree)))]
          (apply conj (empty tree) entries))))))


