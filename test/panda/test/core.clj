(ns panda.test.core
  (:use clojure.test
        panda.core))

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

(deftest test-select-tree
  (is (= (select-tree map-tree
                      #{:a :b})
         {:a "a"
          :b "b"}))
  (is (= (select-tree map-tree
                      #{:a
                        [:branch-one :c]
                        [:branch-two :h]})
         {:a "a"
          :branch-one {:c "c"}
          :branch-two nil}))

  (is (= (select-tree map-tree
                      [:branch-two #{:e
                                     :f}])
         {:branch-two {:e "e"
                       :f "f"}}))
  (is (= (select-tree map-tree
                      #{:a
                        [:branch-two #{:e
                                       [:branch-b :branch-c]}]})
         {:a "a"
          :branch-two {:e "e"
                       :branch-b {:branch-c {:i "i"}}}})))



