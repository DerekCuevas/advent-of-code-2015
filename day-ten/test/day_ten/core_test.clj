(ns day-ten.core-test
  (:require [clojure.test :refer :all]
            [day-ten.core :refer :all]))

(deftest repeatedly-look-and-say-test
  (testing "returns the count of 40 repeated look-and-say's of an input string"
    (is (= (repeatedly-look-and-say 40 "1113122113") 360154))))
