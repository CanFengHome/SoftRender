

int Game_Main(void *parms)
{

static MATRIX4X4 mrot; // general rotation matrix
static float ang_y = 0;      // rotation angle

int index; // looping var


Reset_RENDERLIST4DV1(&rend_list);

// insert polygon into the renderlist
Insert_POLYF4DV1_RENDERLIST4DV1(&rend_list, &poly1);

// generate rotation matrix around y axis
Build_XYZ_Rotation_MATRIX4X4(0, ang_y, 0, &mrot);

// rotate polygon slowly
if (++ang_y >= 360.0) ang_y = 0;

// rotate the local coords of single polygon in renderlist
Transform_RENDERLIST4DV1(&rend_list, &mrot, TRANSFORM_LOCAL_ONLY);

// perform local/model to world transform
Model_To_World_RENDERLIST4DV1(&rend_list, &poly1_pos);

// generate camera matrix
Build_CAM4DV1_Matrix_Euler(&cam, CAM_ROT_SEQ_ZYX);

// apply world to camera transform
World_To_Camera_RENDERLIST4DV1(&rend_list, &cam);

// apply camera to perspective transformation
Camera_To_Perspective_RENDERLIST4DV1(&rend_list, &cam);

// apply screen transform
Perspective_To_Screen_RENDERLIST4DV1(&rend_list, &cam);

// draw instructions
Draw_Text_GDI("Press ESC to exit.", 0, 0, RGB(0,255,0), lpddsback);

// lock the back buffer
DDraw_Lock_Back_Surface();

// render the polygon list
Draw_RENDERLIST4DV1_Wire16(&rend_list, back_buffer, back_lpitch);

// unlock the back buffer
DDraw_Unlock_Back_Surface();

// flip the surfaces
DDraw_Flip();


} // end Game_Main

//////////////////////////////////////////////////////////