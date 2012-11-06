(ns panda.core
  "## Select branches and subtrees.

TODO: some examples"
  (:use [panda.selector :only [selector finder]]))

(defn select-tree
  "Extract a subtree matching `pattern` from `tree`. Like
  `select-keys`, returns a `map` or nil."

  [tree pattern]
  ((selector pattern) tree))

(defn find-branch
  "Extract a branch matching `pattern` from `tree`. Like `find`,
  returns a `MapEntry` or `nil`."

  [tree pattern]
  ((finder pattern) tree))

(defn get-branch
  "Extract a branch matching `pattern` from `tree`. Like `get`,
  returns the value of the branch (leaves out the top-level key)."

  [tree pattern]
  (if-let [entry (find-branch tree)]
    (val entry)))

