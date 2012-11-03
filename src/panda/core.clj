(ns panda.core
  "Select branches and subtrees."
  (:use [panda.selector :only [selector finder]]))

(defn select-tree
  [tree pattern]
  ((selector pattern) tree))

(defn find-branch
  [tree pattern]
  ((finder pattern) tree))

(defn get-branch
  [tree pattern]
  (if-let [entry (find-branch tree)]
    (val entry)))

