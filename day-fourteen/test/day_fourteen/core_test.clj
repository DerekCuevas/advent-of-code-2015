(ns day-fourteen.core-test
  (:require [clojure.test :refer :all]
            [day-fourteen.core :refer :all]
            [clojure.string :refer [split]]))

(def input (split (slurp "resources/input.txt") #"\n"))

(deftest fastest-reindeer-by-position-test
  (testing "fastest reindeer by position"
    (is (= (fastest-reindeer-by-position 2503 input) 2696))))

(deftest fastest-reindeer-by-points-test
  (testing "fastest reindeer by points"
    (is (= (fastest-reindeer-by-points 2503 input) 1084))))
