(ns spherical-houses.core
  (:gen-class))

(def input (slurp "resources/input.txt"))

(defn move [dir [x y]]
  (case dir
    \> [(inc x) y]
    \< [(dec x) y]
    \^ [x (inc y)]
    \v [x (dec y)]))

(defn travel [path]
  (reduce
   (fn [{:keys [visited previous]} dir]
     (let [next (move dir previous)]
       {:visited (update visited next (fnil inc 0))
        :previous next}))
   {:visited {[0 0] 1} :previous [0 0]}
   path))

(defn visited [path]
  (count (:visited (travel path))))
