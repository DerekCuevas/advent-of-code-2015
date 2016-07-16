(ns spherical-houses.core
  (:gen-class))

(defn- move [[x y] dir]
  (case dir
    \> [(inc x) y]
    \< [(dec x) y]
    \^ [x (inc y)]
    \v [x (dec y)]))

(defn- travel [path]
  (->> path
       (reductions move [0 0])
       (into #{})))

(defn visited [path]
  (count (travel path)))

(defn- unravel [s]
  [(take-nth 2 s) (take-nth 2 (rest s))])

(defn visited-by-two-travelers [path]
  (->> (unravel path)
       (map travel)
       (apply into)
       count))
