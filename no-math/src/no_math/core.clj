(ns no-math.core
  (:require [clojure.string :as str])
  (:gen-class))

(def input (str/split (slurp "resources/input.txt") #"\n"))

(defn str->dims [dim-str]
  (->> (str/split dim-str #"x")
       (map read-string)
       (zipmap [:l :w :h])))

(defn side-areas [{:keys [l w h]}]
  (list (* l w) (* w h) (* h l)))

(defn surface-area [dims]
  (->> (side-areas dims)
       (map #(* 2 %))
       (reduce +)))

(defn area-of-smallest-side [dims]
  (apply min (side-areas dims)))

(defn required-wrapping-paper [dims]
  (+ (surface-area dims) (area-of-smallest-side dims)))

(defn total-required-wrapping-paper [dims-list]
  (->> dims-list
       (map str->dims)
       (map required-wrapping-paper)
       (reduce +)))
