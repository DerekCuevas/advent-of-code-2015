(ns spherical-houses.core
  (:gen-class))

(def input (slurp "resources/input.txt"))

(defn- move [dir [x y]]
  (case dir
    \> [(inc x) y]
    \< [(dec x) y]
    \^ [x (inc y)]
    \v [x (dec y)]))

(defn- travel [path]
  (reduce
   (fn [{:keys [visited previous]} dir]
     (let [next (move dir previous)]
       {:visited (conj visited next)
        :previous next}))
   {:visited #{[0 0]}
    :previous [0 0]}
   path))

(defn visited [path]
  (count (:visited (travel path))))

(defn- unravel [s]
  [(take-nth 2 s) (take-nth 2 (rest s))])

(defn visited-by-two-santas [path]
  (let [[santas-path robo-santas-path] (unravel path)]
    (count (into (:visited (travel robo-santas-path))
                 (:visited (travel santas-path))))))
