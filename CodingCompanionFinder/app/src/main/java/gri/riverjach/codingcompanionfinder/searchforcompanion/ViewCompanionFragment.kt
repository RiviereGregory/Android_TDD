/*
 * Copyright (c) 2021 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package gri.riverjach.codingcompanionfinder.searchforcompanion


import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.synnapps.carouselview.CarouselView
import gri.riverjach.codingcompanionfinder.GlideApp
import gri.riverjach.codingcompanionfinder.R
import gri.riverjach.codingcompanionfinder.databinding.FragmentViewCompanionBinding
import gri.riverjach.codingcompanionfinder.models.Animal

class ViewCompanionFragment : Fragment() {

    companion object {
        val ANIMAL: String = "ANIMAL"
    }

    private lateinit var animal: Animal
    private lateinit var petPhotos: ArrayList<String>
    private lateinit var petCaroselView: CarouselView
    private lateinit var viewCompanionFragment: ViewCompanionFragment
    private lateinit var fragmentViewCompanionBinding: FragmentViewCompanionBinding
    private lateinit var viewCompanionViewModel: ViewCompanionViewModel

    val args: ViewCompanionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        animal = args.animal
        viewCompanionFragment = this
        // 1
        fragmentViewCompanionBinding =
            FragmentViewCompanionBinding
                .inflate(inflater, container, false)
        // 2
        viewCompanionViewModel = ViewModelProvider(this).get(ViewCompanionViewModel::class.java)
        // 3
        viewCompanionViewModel.populateFromAnimal(animal)
        // 4
        fragmentViewCompanionBinding.viewCompanionViewModel =
            viewCompanionViewModel
        // 5
        setupPhonNumberOnClick()
        return fragmentViewCompanionBinding.root
    }


    override fun onResume() {
        populatePet()
        super.onResume()
    }

    private fun populatePet() {
        populateTextField(R.id.petName, animal.name)
        populateTextField(
            R.id.city,
            animal.contact.address.city + ", " + animal.contact.address.state
        )
        populateTextField(R.id.age, animal.age)
        populateTextField(R.id.email, animal.contact.email)
        populateTextField(R.id.telephone, animal.contact.phone)
        populateTextField(R.id.sex, animal.gender)
        populateTextField(R.id.size, animal.size)
        populateTextField(R.id.meetTitlePlaceholder, "Meet " + animal.name)
        populateTextField(R.id.description, animal.description)
        populatePhotos()

        populateTextField(R.id.breed, animal.breeds.primary)
    }

    private fun populatePhotos() {
        petPhotos = ArrayList()
        animal.photos.forEach { photo ->
            petPhotos.add(photo.full)
        }

        view?.let {
            petCaroselView = it.findViewById(R.id.petCarouselView)
            petCaroselView.setViewListener { position ->
                val carouselItemView = layoutInflater.inflate(R.layout.companion_photo_layout, null)
                val imageView = carouselItemView.findViewById<ImageView>(R.id.petImage)
                GlideApp.with(viewCompanionFragment).load(petPhotos[position])
                    .fitCenter()
                    .into(imageView)
                carouselItemView
            }
            petCaroselView.pageCount = petPhotos.size
        }
    }

    private fun populateTextField(id: Int, stringValue: String) {
        view?.let {
            (it.findViewById(id) as TextView).text = stringValue
        }
    }

    private fun setupPhonNumberOnClick() {
        fragmentViewCompanionBinding.telephone.setOnClickListener {
            // 1
            Dexter.withActivity(activity)
                .withPermission(Manifest.permission.CALL_PHONE)
                .withListener(
                    object : PermissionListener {
                        // 3
                        override fun onPermissionGranted(
                            response: PermissionGrantedResponse
                        ) {/* ... */
                            val intent = Intent(
                                Intent.ACTION_CALL, Uri.parse(
                                    "tel:" + viewCompanionViewModel.telephone
                                )
                            )
                            startActivity(intent)
                        }

                        override fun onPermissionDenied(
                            response: PermissionDeniedResponse
                        ) {/* ... */
                        }

                        // 2
                        override fun onPermissionRationaleShouldBeShown(
                            permission: PermissionRequest,
                            token: PermissionToken
                        ) {/* ... */
                            token.continuePermissionRequest()
                        }
                    }
                )
                .onSameThread()
                .check()
        }
    }

}
