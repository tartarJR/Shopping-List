package com.tatar.shoppinglist.di.shoppinglist.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ShoppingListsActivityScope {
}