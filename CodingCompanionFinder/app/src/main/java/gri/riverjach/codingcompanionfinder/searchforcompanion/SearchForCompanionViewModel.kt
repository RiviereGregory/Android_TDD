package gri.riverjach.codingcompanionfinder.searchforcompanion

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gri.riverjach.codingcompanionfinder.models.Animal
import gri.riverjach.codingcompanionfinder.retrofit.PetFinderService
import gri.riverjach.codingcompanionfinder.testhooks.IdlingEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

class SearchForCompanionViewModel(val petFinderService: PetFinderService) : ViewModel() {
    val noResultsViewVisiblity: MutableLiveData<Int> = MutableLiveData<Int>()
    val companionLocation: MutableLiveData<String> = MutableLiveData()

    // 1
    val animals: MutableLiveData<ArrayList<Animal>> =
        MutableLiveData<ArrayList<Animal>>()
    var accessToken: String = ""

    fun searchForCompanions() {
        GlobalScope.launch {
            EventBus.getDefault().post(IdlingEntity(1))
            val searchForPetResponse = petFinderService.getAnimals(
                accessToken,
                location = companionLocation.value
            )

            GlobalScope.launch(Dispatchers.Main) {
                if (searchForPetResponse.isSuccessful) {
                    searchForPetResponse.body()?.let {
                        animals.postValue(it.animals)
                        if (it.animals.size > 0) {
                            noResultsViewVisiblity.postValue(View.INVISIBLE)
                        } else {
                            noResultsViewVisiblity.postValue(View.VISIBLE)
                        }
                    }
                } else {
                    noResultsViewVisiblity.postValue(View.VISIBLE)
                }
            }
            EventBus.getDefault().post(IdlingEntity(-1))
        }
    }

}
