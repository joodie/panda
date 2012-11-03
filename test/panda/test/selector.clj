(ns panda.test.selector
  (:use clojure.test
        panda.selector))

(def map-tree
  {:a "a"
   :b "b"
   :branch-one {:c "c"
                :branch-a {:d "d"}}
   :branch-two {:e "e"
                :f "f"
                :branch-b {:g "g"
                           :h "h"
                           :branch-c {:i "i"}}}})


(deftest test-finder
  (is (= [:a "a"]
         ((finder :a) map-tree)))

  (is (= [:branch-one {:c "c"
                       :branch-a {:d "d"}}]
         ((finder :branch-one) map-tree)))

  (is (= [:branch-one {:c "c"
                       :branch-a {:d "d"}}]
         ((finder [:branch-one]) map-tree)))

  (is (= [:branch-one {:c "c"}]
         ((finder [:branch-one :c]) map-tree))))

(deftest test-selector
  (is (= {:a "a"}
         ((selector :a) map-tree))))

