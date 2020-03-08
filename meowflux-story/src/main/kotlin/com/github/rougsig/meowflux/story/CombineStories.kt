package com.github.rougsig.meowflux.story

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.merge

@ExperimentalCoroutinesApi
fun <S : Any, D : Any> combineStories(vararg stories: Story<S, D>): Story<S, D> {
  return { actions, getState, dependencies ->
    stories.map { story -> story(actions, getState, dependencies) }.merge()
  }
}
