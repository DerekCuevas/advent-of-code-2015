(ns day-thirteen.core-test
  (:require [clojure.test :refer :all]
            [day-thirteen.core :refer :all]
            [clojure.string :refer [split]]))

(def input (split (slurp "resources/input.txt") #"\n"))
(def graph (build-graph input))

(deftest max-happiness-test
  (testing "max happiness"
    (is (= (max-happiness graph) 709))))
