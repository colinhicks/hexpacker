(ns hexpacker-stitch-lite.core
  (:require [clojure.set :as set]
            #?(:cljs [hexpacker-stitch-lite.math-interop :as Math])))

(defn min-circles
  "Determines the minimum number of circles of radius r2 that fit inside a circle of radius r1 assuming: A hexagonal configuration, no overlap, the first circle of r2 at the center of the circle of r1, and a 2D plane."
  [r1 r2]
  (let [layer-lim (inc (long (/ r1 (* 2 r2))))
        circles-by-layer (for [l (range layer-lim)] (* 6 l))]
    (inc (reduce + circles-by-layer))))

(defn calculate-xy-coords
  "Given a start point, a distance, and a bearing (in degrees from north), determine the resultant x-y coordinates"
  [{x :x
    y :y 
    distance :distance
    bearing  :bearing}]
  (let [angle (Math/toRadians bearing)
        dx (* distance (Math/cos angle))
        dy (* distance (Math/sin angle))]
    {:x (+ dx x) 
     :y (+ dy y)}))

(defn next-layer-fn
  "Generates a function which, given a vector of hashmaps containing x-y coordinates of a layer of circles forming 'Pascals Triangle', returns the next layer."
  [r2]
  (fn [previous-layer]
    (into []
          (set (flatten
                (map (fn [elem]
                       (let [circle1-xy-coords (calculate-xy-coords {:x (:x elem)
                                                                     :y (:y elem)
                                                                     :distance (* 2 r2)
                                                                     :bearing 0})
                             circle2-xy-coords (calculate-xy-coords {:x (:x elem)
                                                                     :y (:y elem)
                                                                     :distance (* 2 r2)
                                                                     :bearing 60})]
                         [circle1-xy-coords circle2-xy-coords])) previous-layer))))))


(defn pascals-triangle
  "Given a start point and total number of layers, returns a 'pascals-triangle' configuration of circles"
  [start-point layers r2]
  (take layers (iterate (next-layer-fn r2) [start-point])))

(defn rotate-about
  "Given two points and an angle of rotation, return the resultant x-y pair for rotating A about B."
  [pta ptb angle-degrees]
  (let [angle (Math/toRadians angle-degrees)
        a (:x ptb)
        b (:y ptb)
        x (- (:x pta) a)
        y (- (:y pta) b)
        newx (+ (- (* x (Math/cos angle)) (* y (Math/sin angle))) a)
        newy (+ (+ (* x (Math/sin angle)) (* y (Math/cos angle))) b)]
    {:x newx
     :y newy}))

(defn pack-circle
  "Generates vector of x-y coordinates for circle-in-circle hexagonal packing for circles of radii r1 and r2 where r1 > r2."
  [r1 r2 center]
  (set (flatten (let [num-layers (inc (long (/ r1 (* 2 r2))))
                      flat-pascals-triangle (flatten (pascals-triangle center num-layers r2))]
                  (take 6 (iterate (fn [flat-triangle]
                                     (map #(rotate-about %1 center 60) flat-triangle)) flat-pascals-triangle))))))
