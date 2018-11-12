package id.hasaneljabir.bundesligaschedule.presenter

import id.hasaneljabir.bundesligaschedule.model.TeamResponse
import id.hasaneljabir.bundesligaschedule.repository.TeamRepositoryImplementation
import id.hasaneljabir.bundesligaschedule.view.DetailMatchView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber

class DetailMatchPresenter(
    val view : DetailMatchView.View,
    val teamRepositoryImplementation: TeamRepositoryImplementation) : DetailMatchView.Presenter {
    val compositeDisposable = CompositeDisposable()

    override fun getLogoHome(id: String) {
        compositeDisposable.add(teamRepositoryImplementation.getTeamsDetail(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object: ResourceSubscriber<TeamResponse>(){
                override fun onComplete() {}

                override fun onNext(t: TeamResponse) { view.showLogoHome(t.teams[0]) }

                override fun onError(t: Throwable?) {}
            })
        )
    }


    override fun getLogoAway(id:String) {
        compositeDisposable.add(teamRepositoryImplementation.getTeamsDetail(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object: ResourceSubscriber<TeamResponse>(){
                override fun onComplete() {}

                override fun onNext(t: TeamResponse) { view.showLogoAway(t.teams[0]) }

                override fun onError(t: Throwable?) {}
            })
        )
    }

    override fun onDestroyPresenter() { compositeDisposable.dispose() }
}