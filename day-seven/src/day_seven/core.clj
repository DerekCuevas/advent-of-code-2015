(ns day-seven.core
  (:require [clojure.string :refer [split]])
  (:gen-class))

(def ^:private input (split (slurp "resources/input.txt") #"\n"))

;; double check bit-not
(def ^:private gate->fn
  {"AND" bit-and
   "OR" bit-or
   "LSHIFT" bit-shift-left
   "RSHIFT" bit-shift-right
   "NOT" bit-not})

(defn- parse-input [s]
  (let [input (read-string s)]
    (if (number? input) input (keyword input))))

(defn- parse-op
  ([i]
    {:input (parse-input i)})
  ([gate i]
    {:input [(parse-input i)] :gate (gate->fn gate)})
  ([ia gate ib]
    {:input [(parse-input ia) (parse-input ib)] :gate (gate->fn gate)}))

(defn- parse-instruction [s]
  (let [[lhs [rhs]] (map #(split % #" ") (split s #" -> "))]
    (merge {:output (keyword rhs)} (apply parse-op lhs))))
