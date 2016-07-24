(ns day-eight.core
  (:require [clojure.string :refer [split]])
  (:gen-class))

(def input (split (slurp "resources/input.txt") #"\n"))

(defn count-actual [coll]
  (->> (map count coll)
       (reduce +)))

(defn count-in-memory [coll]
  (->> (map (comp count read-string) coll)
       (reduce +)))
