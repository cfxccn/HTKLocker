#LOCAL_PATH := $(call my-dir)
#
#include $(CLEAR_VARS)
#
#LOCAL_MODULE    := libHTK
#LOCAL_SRC_FILES := A.c
#
#include $(BUILD_SHARED_LIBRARY)



LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)  

LOCAL_MODULE    := libHTKCore
LOCAL_SRC_FILES := esignal.c esig_asc.c esig_edr.c esig_nat.c HAdapt.c HArc.c HAudio.c HDict.c HExactMPE.c HFB.c HFBLat.c HGraf.null.c HLabel.c HLat.c HLM.c HMap.c HMath.c HMem.c HModel.c HNet.c HParm.c HRec.c HShell.c HSigP.c HTrain.c HUtil.c HVQ.c HWave.c strarr.c Tool_HCopy.c Tool_HInit.c Tool_HParse.c Tool_HRest.c Tool_HVite.c 

include $(BUILD_STATIC_LIBRARY) 

include $(CLEAR_VARS)

LOCAL_MODULE    := A
LOCAL_SRC_FILES := A.c
LOCAL_WHOLE_STATIC_LIBRARIES := libHTKCore

include $(BUILD_SHARED_LIBRARY) 



