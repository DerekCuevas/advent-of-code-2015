(ns spherical-houses.core-test
  (:require [clojure.test :refer :all]
            [spherical-houses.core :refer :all]))

(def input (slurp "resources/input.txt"))

(deftest visited-test
  (testing "Returns number of unique locations visited by traveling path."
    (is (= (visited input) 2565))))

(deftest visited-test
  (testing "Returns number of unique locations visited by traveling path with two travelers."
    (is (= (visited-by-two-travelers input) 2639))))
