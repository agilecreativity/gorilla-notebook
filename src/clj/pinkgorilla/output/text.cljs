(ns pinkgorilla.output.text
  (:require
   [clojure.string :as string]
   ))



(defn line-with-br [t]
  [:div
   [:span.font-mono.text-lg.whitespace-pre t]
   [:br]])

(defn text [t]
  (let [lines (string/split t #"\n")]
    (into [:div {:gorilla_ui "text"}] (map line-with-br lines))))


(defn output-text
  [output _]
  (if-let [content (:content output)]
  {:type :html
   :content (text (:text content))
   :value (pr-str content)}))