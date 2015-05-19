LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)  
LOCAL_MODULE    := libHTKCore
LOCAL_SRC_FILES := esignal.c esig_asc.c esig_edr.c esig_nat.c HAdapt.c HArc.c HAudio.c HDict.c HExactMPE.c HFB.c HFBLat.c HGraf.null.c HLabel.c HLat.c HLM.c HMap.c HMath.c HMem.c HModel.c HNet.c HParm.c HRec.c HShell.c HSigP.c HTrain.c HUtil.c HVQ.c HWave.c strarr.c Tool_HCopy.c Tool_HInit.c Tool_HParse.c Tool_HRest.c Tool_HVite.c
include $(BUILD_STATIC_LIBRARY) 

include $(CLEAR_VARS)
LOCAL_MODULE    := HCopy
LOCAL_SRC_FILES := com_flo_util_HCopyFunc.c
LOCAL_WHOLE_STATIC_LIBRARIES := libHTKCore
include $(BUILD_SHARED_LIBRARY) 

include $(CLEAR_VARS)
LOCAL_MODULE    := HInit
LOCAL_SRC_FILES := com_flo_util_HInitFunc.c
LOCAL_WHOLE_STATIC_LIBRARIES := libHTKCore
include $(BUILD_SHARED_LIBRARY) 

include $(CLEAR_VARS)
LOCAL_MODULE    := HRest
LOCAL_SRC_FILES := com_flo_util_HRestFunc.c
LOCAL_WHOLE_STATIC_LIBRARIES := libHTKCore
include $(BUILD_SHARED_LIBRARY) 

include $(CLEAR_VARS)
LOCAL_MODULE    := HRest2
LOCAL_SRC_FILES := com_flo_util_HRest2Func.c
LOCAL_WHOLE_STATIC_LIBRARIES := libHTKCore
include $(BUILD_SHARED_LIBRARY) 



include $(CLEAR_VARS)
LOCAL_MODULE    := HParse
LOCAL_SRC_FILES := com_flo_util_HParseFunc.c
LOCAL_WHOLE_STATIC_LIBRARIES := libHTKCore
include $(BUILD_SHARED_LIBRARY) 

include $(CLEAR_VARS)
LOCAL_MODULE    := HClear
LOCAL_SRC_FILES := com_flo_util_HClearFunc.c
LOCAL_WHOLE_STATIC_LIBRARIES := libHTKCore
include $(BUILD_SHARED_LIBRARY) 


include $(CLEAR_VARS)
LOCAL_MODULE    := HViteE
LOCAL_SRC_FILES := com_HVite.c
LOCAL_FORCE_STATIC_EXECUTABLE := true
LOCAL_STATIC_LIBRARIES := libHTKCore
include $(BUILD_EXECUTABLE) 