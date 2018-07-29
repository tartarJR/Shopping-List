package com.tatar.shoppinglist.di.activeshoppinglists.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActiveShoppingListsScope {
}