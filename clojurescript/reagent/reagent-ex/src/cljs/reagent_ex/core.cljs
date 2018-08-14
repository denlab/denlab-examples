(ns reagent-ex.core
    (:require [reagent.core :as reagent :refer [atom]]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]
              [reagent.core :as r]
))


;; components
;;   simple-component
(defn simple-component []
  [:div
   [:p "I am a component!"]
   [:p.someclass
    "I have " [:strong "bold"]
    [:span {:style {:color "red"}} " and red "] "text."]])
;;   simple-component
(defn nested-details []
  [:div
   [:details [:summary "summary1"]
    [:details [:summary "sub1"]
     [:details [:summary :sub1SubA] "stuff"]
     ]
    [:details [:summary "sub2"]
     [:details [:summary :sub2SubA] "stuff"]
     ]
    [:details [:summary "sub3"]
     [:details [:summary :sub3SubA] "stuff"]
     ]
    ]

])


;;   lister
(defn lister [items]
  [:ul
   (for [item items]
     ^{:key item} [:li "Item " item])])

(defn lister-user []
  [:div
   "Here is a list:"
   [lister (range 3)]])

(defn simple-parent []
  [:div
   [:p "I include simple-component."]
   [:details
    [:summary "a simple component"]
    [simple-component]]])

;;   counting
(def click-count (r/atom 0))

(defn counting-component []
  [:div
   "The atom " [:code "click-count"] " has value: "
   @click-count ". "
   [:input {:type "button" :value "Click me!"
            :on-click #(swap! click-count inc)}]])

;; -------------------------
;; Views

(defn home-page []
  [:div [:h2 "Welcome to reagent-ex"]
   [:div [:a {:href "/about"} "go to about page"]]
   [:details [:summary "simple-parent"] [simple-parent]]
   [:details [:summary "lister-user"] [lister-user]]
   [:details [:summary "counting-component"] [counting-component]]
   [:details [:summary "nested-details"] [nested-details]]
])

(defn about-page []
  [:div [:h2 "About reagent-ex"]
   [:div [:a {:href "/"} "go to the home page"]]])

;; -------------------------
;; Routes

(defonce page (atom #'home-page))

(defn current-page []
  [:div
   [:div
    [:div [@page]]
]

]

  )

(secretary/defroute "/" []
  (reset! page #'home-page))

(secretary/defroute "/about" []
  (reset! page #'about-page))

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (accountant/configure-navigation!
    {:nav-handler
     (fn [path]
       (secretary/dispatch! path))
     :path-exists?
     (fn [path]
       (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (mount-root))
