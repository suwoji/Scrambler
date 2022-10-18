package ru.startandroid.develop.scrambler

interface MVPPresenter<V : MVPView> {

    fun onAttach(view: V?)

    fun onDetach()

    fun getView(): V?

}