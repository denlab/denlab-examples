#!/usr/bin/env bash

http POST 'http://localhost:3000/pizzas' <<< '{"name": "mypiz","price": 1,"hot": true,"description": "good","toppings": ["olives"]}'
