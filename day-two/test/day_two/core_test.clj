(ns day-two.core-test
  (:require [clojure.test :refer :all]
            [clojure.string :refer [split]]
            [day-two.core :refer :all]))

(def input (split (slurp "resources/input.txt") #"\n"))

(deftest total-required-wrapping-paper-test
  (testing "total required wrapping paper"
    (is (= (total-required-wrapping-paper input) 1588178))))

(deftest total-required-ribbon-test
  (testing "total required ribbon"
    (is (= (total-required-ribbon input) 3783758))))
