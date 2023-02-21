package com.example.woodpeaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.woodpeaker.databinding.ActivityThreeDimentionalModelBinding
import com.google.ar.core.Config
import com.google.ar.sceneform.rendering.CameraStream
import com.google.ar.sceneform.ux.ArFragment

class ThreeDimentionalModelActivity : AppCompatActivity() {
    lateinit var binding:ActivityThreeDimentionalModelBinding
    lateinit var arFragment: ArFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityThreeDimentionalModelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val link="https://firebasestorage.googleapis.com/v0/b/woodpeaker-39311.appspot.com/o/models%2Fmodel2.glb?alt=media&token=e3aca389-8f77-4b1f-9769-ff3eeb6b8e88"
        arFragment=(supportFragmentManager.findFragmentById(R.id.arFragment) as ArFragment)
        arFragment.setOnTapPlaneGlbModel(link)

        arFragment.apply {
            setOnSessionConfigurationListener { session, config ->
                if (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
                    config.depthMode = Config.DepthMode.AUTOMATIC
                }
            }
            setOnViewCreatedListener { arSceneView ->
                // Available modes: DEPTH_OCCLUSION_DISABLED, DEPTH_OCCLUSION_ENABLED
                arSceneView.cameraStream.depthOcclusionMode =
                    CameraStream.DepthOcclusionMode.DEPTH_OCCLUSION_ENABLED
            }
        }

    }
}