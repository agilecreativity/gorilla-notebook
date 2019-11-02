;; gorilla-repl.fileformat = 2

;; **
;;; # Reagent Widgets with Dynamic State
;; **

;; @@
; Define Namespace for your notebook and require namespaces 
(ns pacific-pond  
  (:require 
     [pinkgorilla.ui.widget :refer [widget!]] 
     [pinkgorilla.ui.hiccup :refer [html!]] 
     [pinkgorilla.ui.reagent :refer [reagent!]]))
;; @@
;; ->
;;; 
;; <-
;; =>
;;; ["^ ","~:type","html","~:content",["span",["^ ","~:class","clj-nil"],"nil"],"~:value","nil"]
;; <=

;; @@
; TEST to see if reagent atom widget-state is passed to reagent.
; Passing :h1 the full state is a little stupid because h1 cannot render specific properties of 
; the state. To do (:name :widget-state) we need full dynamic clojurescript compilation. 
; This test is still useful because we can test if the widget-state injection works.
(reagent! '[:h1 :widget-state])
;; @@
;; ->
;;; 
;; <-
;; =>
;;; ["^ ","~:type","reagent","~:content",["h1","widget-state"],"~:value","[:h1 :widget-state]"]
;; <=

;; @@
; TEST to Render custom made Reagent Component.
(reagent! 
  '[widget.hello/love
    "beer"])
;; @@
;; ->
;;; 
;; <-
;; =>
;;; ["^ ","~:type","reagent","~:content",["love","beer"],"~:value","[widget.hello/love \"beer\"]"]
;; <=

;; @@
; Test if the 2 love widgets render different text in the notebook if displayed at the same time.
(reagent! 
  '[widget.hello/love
    "wine"])
;; @@
;; ->
;;; 
;; <-
;; =>
;;; ["^ ","~:type","reagent","~:content",["love","wine"],"~:value","[widget.hello/love \"wine\"]"]
;; <=

;; @@
; TEST to see if a textbox will get rendered that is give the widget-state reagent atom
(reagent! 
  '[text :widget-state :name])
;; @@
;; ->
;;; 
;; <-
;; =>
;;; ["^ ","~:type","reagent","~:content",["text","widget-state","name"],"~:value","[text :widget-state :name]"]
;; <=

;; @@
; TEST to render a different property.
(reagent! 
  '[text :widget-state :time])
;; @@
;; ->
;;; 
;; <-
;; =>
;;; ["^ ","~:type","reagent","~:content",["text","widget-state","time"],"~:value","[text :widget-state :time]"]
;; <=

;; @@
; two textboxes in one reagent structure
; Change the text of the widget and you will see that the widgets above do change also!! :-)
(reagent! 
  '[:div 
    [text :widget-state :name]
    [text :widget-state :time]
    [:h4 "super duper"]]) 
;; @@
;; ->
;;; 
;; <-
;; =>
;;; ["^ ","~:type","reagent","~:content",["div",["text","widget-state","name"],["text","widget-state","time"],["h4","super duper"]],"~:value","[:div [text :widget-state :name] [text :widget-state :time] [:h4 \"super duper\"]]"]
;; <=

;; @@
(+ 5 45)
;; @@
;; ->
;;; 
;; <-
;; =>
;;; ["^ ","~:type","html","~:content",["span",["^ ","~:class","clj-long"],"50"],"~:value","50"]
;; <=

;; @@
; TEST to render a re-com components

;; @@
;; ->
;;; 
;; <-

;; @@
(reagent! 
  '[rcm/h-box :gap "30px" :align    :center :children
    [[:span "hi"] 
     [rcm/button :label "No Clicking !!"]
     [:span "ho"]]])
;; @@
;; ->
;;; 
;; <-
;; =>
;;; ["^ ","~:type","reagent","~:content",["h-box","gap","30px","align","center","children",[["span","hi"],["button","label","No Clicking !!"],["span","ho"]]],"~:value","[rcm/h-box :gap \"30px\" :align :center :children [[:span \"hi\"] [rcm/button :label \"No Clicking !!\"] [:span \"ho\"]]]"]
;; <=

;; @@
; SLIDERS DO NOT WORK, BECAUSE WE CANNOT GIVE :model :on-change
(reagent! 
  '[rcm/h-box :gap "30px" :align    :center :children
    [[:span "hi"] 
     [rcm/slider]sa
     [:span "ho"]]])

;; @@
;; ->
;;; 
;; <-
;; =>
;;; ["^ ","~:type","reagent","~:content",["h-box","gap","30px","align","center","children",[["span","hi"],["slider"],["span","ho"]]],"~:value","[rcm/h-box :gap \"30px\" :align :center :children [[:span \"hi\"] [rcm/slider] [:span \"ho\"]]]"]
;; <=

;; @@

;; @@
;; ->
;;; 
;; <-
