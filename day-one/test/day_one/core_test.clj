(ns day-one.core-test
  (:require [clojure.test :refer :all]
            [day-one.core :refer :all]))

(def input (slurp "resources/input.txt"))

(deftest travel-test
  (testing "travel"
    (is (= (travel input) 138))))

(deftest floors-to-basement-test
  (testing "floors to basement"
    (is (= (floors-to-basement input) 1771))))
