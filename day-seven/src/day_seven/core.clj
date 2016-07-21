(ns day-seven.core
  (:require [clojure.string :refer [split]])
  (:gen-class))

(def ^:private input (split (slurp "resources/input.txt") #"\n"))

;; bit-not?
(def ^:private gate->fn
  {"AND" bit-and
   "OR" bit-or
   "LSHIFT" bit-shift-left
   "RSHIFT" bit-shift-right
   "NOT" bit-not})

(defn- number-or-keyword [s]
  (let [read (read-string s)]
    (if (number? read) read (keyword read))))

;; maybe use multi-method here for dynamic dispatch
;; on :type (no-op, unary, binary)?
(defn- parse-no-op [[i]]
  {:input (number-or-keyword i)})

(defn- parse-unary-op [[gate i]]
  {:input [(number-or-keyword i)] :gate (gate->fn gate)})

(defn- parse-binary-op [[ia gate ib]]
  {:input [(number-or-keyword ia) (number-or-keyword ib)]
   :gate (gate->fn gate)})

(def ^:private lhs-count->parse-fn
  {1 parse-no-op
   2 parse-unary-op
   3 parse-binary-op})

;; {:output _ :input [_ _] :gate}
(defn- parse-instruction [s]
  (let [[lhs [rhs]] (map #(split % #" ") (split s #" -> "))]
    (merge {:output (keyword rhs)}
           ((lhs-count->parse-fn (count lhs)) lhs))))
