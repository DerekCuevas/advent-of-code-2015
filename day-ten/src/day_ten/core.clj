(ns day-ten.core
  (:gen-class))

(defn- look-and-say [s]
  (let [uniques (partition-by identity s)]
    (apply str (-> (map count uniques)
                   (interleave (map dedupe uniques))
                   flatten))))

(defn repeatedly-look-and-say [n s]
  (->> (range n)
       (reduce (fn [previous _] (look-and-say previous)) s)
       count))
