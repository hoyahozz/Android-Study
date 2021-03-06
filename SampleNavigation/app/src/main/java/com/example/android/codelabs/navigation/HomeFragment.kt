/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.codelabs.navigation

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView

/**
 * Fragment used to show how to navigate to another destination
 */
class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        //TODO STEP 5 - Set an OnClickListener, using Navigation.createNavigateOnClickListener()
//        val button = view.findViewById<Button>(R.id.navigate_destination_button)
//        button?.setOnClickListener {
//            // navigate() 호출 시 NavController 에서 개발자를 대신해 startActivity() 호출
//            findNavController().navigate(R.id.flow_step_one_dest, null)
//        }
        //TODO END STEP 5

        //TODO STEP 6 - Set NavOptions
        // 애니메이션 추가
//        val options = navOptions {
//            anim {
//                enter = R.anim.slide_in_right
//                exit = R.anim.slide_out_left
//                popEnter = R.anim.slide_in_left
//                popExit = R.anim.slide_out_right
//            }
//        }
//
//        view.findViewById<Button>(R.id.navigate_destination_button).setOnClickListener {
//            findNavController().navigate(R.id.flow_step_one_dest, null, options)
//        }
        //TODO END STEP 6

        //TODO STEP 7.2 - Update the OnClickListener to navigate using an action
//        view.findViewById<Button>(R.id.navigate_action_button).setOnClickListener {
//            Navigation.createNavigateOnClickListener(R.id.next_action, null)
//        }
        //TODO END STEP 7.2

//        view.findViewById<Button>(R.id.shopping_cart).setOnClickListener {
//            Navigation.createNavigateOnClickListener(R.id.shopping_action, null)
//        }
        // safe args 방향 클래스
        view.findViewById<Button>(R.id.navigate_action_button).setOnClickListener {
            val flowStepNumberArgs = 1
            val action = HomeFragmentDirections.nextAction(flowStepNumberArgs) // 방향 안내 클래스
            // XML에서 각 인수에 기본 값을 제공할 수 있음.
            findNavController().navigate(action)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.shopping_cart) {
            Toast.makeText(requireContext(), "쇼핑 ON", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.shopping_action, null)
        }

        return super.onOptionsItemSelected(item)
    }
}
