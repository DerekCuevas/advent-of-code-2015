(ns day-ten.core-test
  (:require [clojure.test :refer :all]
            [day-ten.core :refer :all]))

(def input (slurp "resources/input.txt"))

(deftest repeatedly-look-and-say-test
  (testing "returns the count of n repeated look-and-say's of an input string"
    (is (= (repeatedly-look-and-say 40 input) 360154))
    (is (= (repeatedly-look-and-say 50 input) 5103798))))
