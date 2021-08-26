package com.example.testmpandroidchart.fragment

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testmpandroidchart.R
import com.example.testmpandroidchart.fragment.adapter.SensorAdapter
import com.example.testmpandroidchart.source.DataModel
import com.example.testmpandroidchart.source.Data
import kotlinx.android.synthetic.main.fragment_start.*

class StartFragment : Fragment() {

    private val adapter: SensorAdapter by lazy { SensorAdapter(onListItemClickListener) }

    private val onListItemClickListener: SensorAdapter.OnListItemClickListener =
        object : SensorAdapter.OnListItemClickListener {
            override fun onItemClick(listItemData: Data) {
//                toast(listItemData.temp)
                view?.findNavController()?.navigate(R.id.action_startFragment_to_chartFragment)
//                val editMarkDialog = EditMarkDialogFragment()
//                val args = Bundle()
//                args.putInt("index", listItemData.id)
//                args.putString("name", listItemData.name)
//                args.putString("description", listItemData.description)
//                editMarkDialog.arguments = args
//                editMarkDialog.show(supportFragmentManager, "dlg1")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        start_recyclerview.adapter = adapter
        start_recyclerview.layoutManager = GridLayoutManager(activity, 2)

        val data = DataModel().data

        adapter.setData(data)

    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

}