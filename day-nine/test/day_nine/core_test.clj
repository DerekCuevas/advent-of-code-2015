(ns day-nine.core-test
  (:require [clojure.test :refer :all]
            [clojure.string :refer [split]]
            [day-nine.core :refer :all]))

(def input (split (slurp "resources/input.txt") #"\n"))
(def graph (build-graph input))

(deftest shortest-route-test
  (testing "returns distance of the shortest route"
    (is (= (shortest-route graph) 207))))

(deftest longest-route-test
  (testing "returns distance of the longest route"
    (is (= (longest-route graph) 804))))
