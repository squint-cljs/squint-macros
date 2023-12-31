(ns applied-science.js-interop
  (:refer-clojure :exclude [defn get get-in fn let select-keys assoc!]))

(defmacro lit [x] x)

(defmacro defn [& body]
  `(clojure.core/defn ~@body))

(defmacro get-in [& body]
  `(clojure.core/get-in ~@body))

(defmacro fn [& body]
  `(clojure.core/fn ~@body))

(defmacro let [& body]
  `(clojure.core/let ~@body))

(defmacro call-in [obj path & args]
  `(let [parent# (clojure.core/get-in ~obj ~(vec (butlast path)))
         f# (clojure.core/get parent# ~(last path))]
     (.call f# parent# ~@args)))

(defmacro call [obj f & args]
  (list* (symbol (str "." (name f))) obj args))

(defmacro !set [obj k v]
  `(do (cljs.core/set! ~(list (symbol (str ".-" (name k))) obj) ~v)
       ~obj))

(defmacro extend! [obj other]
  `(js/Object.assign ~obj ~other))

(defmacro push! [obj v]
  `(doto ~obj
     (.push ~v)))

(defmacro select-keys [obj ks]
  `(clojure.core/select-keys ~obj ~ks))

(defmacro assoc! [& body]
  `(clojure.core/assoc! ~@body))

(defmacro obj [& body]
  `(cljs.core/js-obj ~@body))

(defmacro get [& body]
  `(cljs.core/get ~@body))
