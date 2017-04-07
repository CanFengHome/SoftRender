// T3DLIB4.CPP - Game Engine Part IV, Basic Math Engine Part I
 
// INCLUDES ///////////////////////////////////////////////////

#define WIN32_LEAN_AND_MEAN  

//#ifndef INITGUID
//#define INITGUID       // you need this or DXGUID.LIB
//#endif

#include <windows.h>   // include important windows stuff
#include <windowsx.h> 
#include <mmsystem.h>
#include <objbase.h>
#include <iostream.h> // include important C/C++ stuff
#include <conio.h>
#include <stdlib.h>
#include <malloc.h>
#include <memory.h>
#include <string.h>
#include <stdarg.h>
#include <stdio.h>
#include <math.h>
#include <io.h>
#include <fcntl.h>
#include <direct.h>
#include <wchar.h>

#include <ddraw.h>      // needed for defs in T3DLIB1.H 
#include "T3DLIB1.H"    // T3DLIB4 is based on some defs in this 
#include "T3DLIB4.H"

// DEFINES ////////////////////////////////////////////////

// TYPES //////////////////////////////////////////////////

// PROTOTYPES /////////////////////////////////////////////

// EXTERNALS /////////////////////////////////////////////

extern HWND main_window_handle;     // access to main window handle in main module

// GLOBALS ////////////////////////////////////////////////

// FUNCTIONS //////////////////////////////////////////////

FIXP16 FIXP16_MUL(FIXP16 fp1, FIXP16 fp2)
{
// this function computes the product fp_prod = fp1*fp2
// using 64 bit math, so as not to loose precission

FIXP16 fp_prod; // return the product

_asm {
     mov eax, fp1      // move into eax fp2
     imul fp2          // multiply fp1*fp2
     shrd eax, edx, 16 // result is in 32:32 format 
                       // residing at edx:eax
                       // shift it into eax alone 16:16
     // result is sitting in eax
     } // end asm

} // end FIXP16_MUL

///////////////////////////////////////////////////////////////

FIXP16 FIXP16_DIV(FIXP16 fp1, FIXP16 fp2)
{
// this function computes the quotient fp1/fp2 using
// 64 bit math, so as not to loose precision

_asm {
     mov eax, fp1      // move dividend into eax
     cdq               // sign extend it to edx:eax
     shld edx, eax, 16 // now shift 16:16 into position in edx
     sal eax, 16       // and shift eax into position since the
                       // shld didn't move it -- DUMB! uPC
     idiv fp2          // do the divide
     // result is sitting in eax     
     } // end asm

} // end FIXP16_DIV

///////////////////////////////////////////////////////////////

void FIXP16_Print(FIXP16 fp)
{
// this function prints out a fixed point number
Write_Error("\nfp=%f", (float)(fp)/FIXP16_MAG);

} // end FIXP16_Print

///////////////////////////////////////////////////////////////

void QUAT_Add(QUAT_PTR q1, QUAT_PTR q2, QUAT_PTR qsum)
{
// this function adds two quaternions
qsum->x = q1->x + q2->x;
qsum->y = q1->y + q2->y;
qsum->z = q1->z + q2->z;
qsum->w = q1->w + q2->w;

} // end QUAT_Add

///////////////////////////////////////////////////////////////

 void QUAT_Sub(QUAT_PTR q1, QUAT_PTR q2, QUAT_PTR qdiff)
{
// this function subtracts two quaternions, q1-q2
qdiff->x = q1->x - q2->x;
qdiff->y = q1->y - q2->y;
qdiff->z = q1->z - q2->z;
qdiff->w = q1->w - q2->w;

} // end QUAT_Sub

///////////////////////////////////////////////////////////////

void QUAT_Conjugate(QUAT_PTR q, QUAT_PTR qconj)
{
// this function computes the conjugate of a quaternion
qconj->x = -q->x;
qconj->y = -q->y;
qconj->z = -q->z;
qconj->w = q->w;
} // end QUAT_Conjugate

///////////////////////////////////////////////////////////////

void QUAT_Scale(QUAT_PTR q, float scale, QUAT_PTR qs)
{
// this function scales a quaternion and returns it 
qs->x = scale*q->x;
qs->y = scale*q->y;
qs->z = scale*q->z;
qs->w = scale*q->w;

} // end QUAT_Scale

///////////////////////////////////////////////////////////////

void QUAT_Scale(QUAT_PTR q, float scale)
{
// this function scales a quaternion in place
q->x*=scale;
q->y*=scale;
q->z*=scale;
q->w*=scale;

} // end QUAT_Scale

//////////////////////////////////////////////////////////////

float QUAT_Norm(QUAT_PTR q)
{
// returns the length or norm of a quaterion
return(sqrtf(q->w*q->w + q->x*q->x + q->y*q->y + q->z*q->z));

} // end QUAT_Norm

//////////////////////////////////////////////////////////////

float QUAT_Norm2(QUAT_PTR q)
{
// returns the length or norm of a quaterion squared
return(q->w*q->w + q->x*q->x + q->y*q->y + q->z*q->z);

} // end QUAT_Norm2

/////////////////////////////////////////////////////////////

void QUAT_Normalize(QUAT_PTR q, QUAT_PTR qn)
{
// this functions normalizes the sent quaternion and 
// returns it

// compute 1/length
float qlength_inv = 1.0/(sqrtf(q->w*q->w + q->x*q->x + q->y*q->y + q->z*q->z));

// now normalize
qn->w=q->w*qlength_inv;
qn->x=q->x*qlength_inv;
qn->y=q->y*qlength_inv;
qn->z=q->z*qlength_inv;

} // end QUAT_Normalize

/////////////////////////////////////////////////////////////

void QUAT_Normalize(QUAT_PTR q)
{
// this functions normalizes the sent quaternion in place

// compute length
float qlength_inv = 1.0/(sqrtf(q->w*q->w + q->x*q->x + q->y*q->y + q->z*q->z));

// now normalize
q->w*=qlength_inv;
q->x*=qlength_inv;
q->y*=qlength_inv;
q->z*=qlength_inv;

} // end QUAT_Normalize

//////////////////////////////////////////////////////////////

void QUAT_Unit_Inverse(QUAT_PTR q, QUAT_PTR qi)
{
// this function computes the inverse of a unit quaternion
// and returns the result
// the inverse of a unit quaternion is the conjugate :)
qi->w =  q->w;
qi->x = -q->x;
qi->y = -q->y;
qi->z = -q->z;
} // end QUAT_Unit_Inverse

//////////////////////////////////////////////////////////////

void QUAT_Unit_Inverse(QUAT_PTR q)
{
// this function computes the inverse of a unit quaternion
// in place
// the inverse of a unit quaternion is the conjugate :)
q->x = -q->x;
q->y = -q->y;
q->z = -q->z;
} // end QUAT_Unit_Inverse

/////////////////////////////////////////////////////////////

void QUAT_Inverse(QUAT_PTR q, QUAT_PTR qi)
{
// this function computes the inverse of a general quaternion
// and returns result
// in general, q-1 = *q/|q|2
// compute norm squared
float norm2_inv = 1.0/(q->w*q->w + q->x*q->x + q->y*q->y + q->z*q->z);

// and plug in
qi->w =  q->w*norm2_inv;
qi->x = -q->x*norm2_inv;
qi->y = -q->y*norm2_inv;
qi->z = -q->z*norm2_inv;

} // end QUAT_Inverse

/////////////////////////////////////////////////////////////

void QUAT_Inverse(QUAT_PTR q)
{
// this function computes the inverse of a general quaternion
// in place
// in general, q-1 = *q/|q|2
// compute norm squared
float norm2_inv = 1.0/(q->w*q->w + q->x*q->x + q->y*q->y + q->z*q->z);

// and plug in
q->w =  q->w*norm2_inv;
q->x = -q->x*norm2_inv;
q->y = -q->y*norm2_inv;
q->z = -q->z*norm2_inv;

} // end QUAT_Inverse

/////////////////////////////////////////////////////////////

void QUAT_Mul(QUAT_PTR q1, QUAT_PTR q2, QUAT_PTR qprod)
{
// this function multiplies two quaternions

// this is the brute force method
//qprod->w = q1->w*q2->w - q1->x*q2->x - q1->y*q2->y - q1->z*q2->z;
//qprod->x = q1->w*q2->x + q1->x*q2->w + q1->y*q2->z - q1->z*q2->y;
//qprod->y = q1->w*q2->y - q1->x*q2->z + q1->y*q2->w - q1->z*q2->x;
//qprod->z = q1->w*q2->z + q1->x*q2->y - q1->y*q2->x + q1->z*q2->w;

// this method was arrived at basically by trying to factor the above
// expression to reduce the # of multiplies

float prd_0 = (q1->z - q1->y) * (q2->y - q2->z);
float prd_1 = (q1->w + q1->x) * (q2->w + q2->x);
float prd_2 = (q1->w - q1->x) * (q2->y + q2->z);
float prd_3 = (q1->y + q1->z) * (q2->w - q2->x);
float prd_4 = (q1->z - q1->x) * (q2->x - q2->y);
float prd_5 = (q1->z + q1->x) * (q2->x + q2->y);
float prd_6 = (q1->w + q1->y) * (q2->w - q2->z);
float prd_7 = (q1->w - q1->y) * (q2->w + q2->z);

float prd_8 = prd_5 + prd_6 + prd_7;
float prd_9 = 0.5 * (prd_4 + prd_8);

// and finally build up the result with the temporary products

qprod->w = prd_0 + prd_9 - prd_5;
qprod->x = prd_1 + prd_9 - prd_8;
qprod->y = prd_2 + prd_9 - prd_7;
qprod->z = prd_3 + prd_9 - prd_6;

} // end QUAT_Mul

///////////////////////////////////////////////////////////

void QUAT_Triple_Product(QUAT_PTR q1, QUAT_PTR q2, QUAT_PTR q3, 
                         QUAT_PTR qprod)
{
// this function computes q1*q2*q3 in that order and returns 
// the results in qprod

QUAT qtmp;
QUAT_Mul(q1,q2,&qtmp);
QUAT_Mul(&qtmp, q3, qprod);

} // end QUAT_Triple_Product

///////////////////////////////////////////////////////////

void VECTOR3D_Theta_To_QUAT(QUAT_PTR q, VECTOR3D_PTR v, float theta)
{
// initializes a quaternion based on a 3d direction vector and angle
// note the direction vector must be a unit vector
// and the angle is in rads

float theta_div_2 = (0.5)*theta; // compute theta/2

// compute the quaterion, note this is from chapter 4
// pre-compute to save time
float sinf_theta = sinf(theta_div_2);

q->x = sinf_theta * v->x;
q->y = sinf_theta * v->y;
q->z = sinf_theta * v->z;
q->w = cosf( theta_div_2 );

} // end VECTOR3D_Theta_To_QUAT

///////////////////////////////////////////////////////////////

 void VECTOR4D_Theta_To_QUAT(QUAT_PTR q, VECTOR4D_PTR v, float theta)
{
// initializes a quaternion based on a 4d direction vector and angle
// note the direction vector must be a unit vector
// and the angle is in rads

float theta_div_2 = (0.5)*theta; // compute theta/2

// compute the quaterion, note this is from chapter 4

// pre-compute to save time
float sinf_theta = sinf(theta_div_2);

q->x = sinf_theta * v->x;
q->y = sinf_theta * v->y;
q->z = sinf_theta * v->z;
q->w = cosf( theta_div_2 );

} // end VECTOR4D_Theta_to_QUAT

///////////////////////////////////////////////////////////////

void EulerZYX_To_QUAT(QUAT_PTR q, float theta_z, float theta_y, float theta_x)
{
// this function intializes a quaternion based on the zyx
// multiplication order of the angles that are parallel to the
// zyx axis respectively, note there are 11 other possibilities
// this is just one, later we may make a general version of the
// the function

// precompute values
float cos_z_2 = 0.5*cosf(theta_z);
float cos_y_2 = 0.5*cosf(theta_y);
float cos_x_2 = 0.5*cosf(theta_x);

float sin_z_2 = 0.5*sinf(theta_z);
float sin_y_2 = 0.5*sinf(theta_y);
float sin_x_2 = 0.5*sinf(theta_x);

// and now compute quaternion
q->w = cos_z_2*cos_y_2*cos_x_2 + sin_z_2*sin_y_2*sin_x_2;
q->x = cos_z_2*cos_y_2*sin_x_2 - sin_z_2*sin_y_2*cos_x_2;
q->y = cos_z_2*sin_y_2*cos_x_2 + sin_z_2*cos_y_2*sin_x_2;
q->z = sin_z_2*cos_y_2*cos_x_2 - cos_z_2*sin_y_2*sin_x_2;

} // EulerZYX_To_QUAT

///////////////////////////////////////////////////////////////

void QUAT_To_VECTOR3D_Theta(QUAT_PTR q, VECTOR3D_PTR v, float *theta)
{
// this function converts a unit quaternion into a unit direction
// vector and rotation angle about that vector

// extract theta
*theta = acosf(q->w);

// pre-compute to save time
float sinf_theta_inv = 1.0/sinf(*theta);

// now the vector
v->x    = q->x*sinf_theta_inv;
v->y    = q->y*sinf_theta_inv;
v->z    = q->z*sinf_theta_inv;

// multiply by 2
*theta*=2;

} // end QUAT_To_VECTOR3D_Theta

////////////////////////////////////////////////////////////

void QUAT_Print(QUAT_PTR q, char *name="q")
{
// this function prints out a quaternion
Write_Error("\n%s=%f+[(%f)i + (%f)j + (%f)k]", 
       name, q->w, q->x, q->y, q->z);

} // end QUAT_Print









/////////////////////////////////////////////////////////////////

void Init_Parm_Line2D(POINT2D_PTR p_init, 
                      POINT2D_PTR p_term, PARMLINE2D_PTR p)
{
// this initializes a parametric 2d line, note that the function
// computes v=p_pend - p_init, thus when t=0 the line p=p0+v*t = p0
// and whne t=1, p=p1, this way the segement is traced out from
// p0 to p1 via 0<= t <= 1

// start point
VECTOR2D_INIT(&(p->p0), p_init);

// end point
VECTOR2D_INIT(&(p->p1), p_term);

// now compute direction vector from p0->p1
VECTOR2D_Build(p_init, p_term, &(p->v));

} // end Init_Parm_Line2D

/////////////////////////////////////////////////////////////////

void Init_Parm_Line3D(POINT3D_PTR p_init, 
                      POINT3D_PTR p_term, PARMLINE3D_PTR p)
{
// this initializes a parametric 3d line, note that the function
// computes v=p_pend - p_init, thus when t=0 the line p=p0+v*t = p0
// and whne t=1, p=p1, this way the segement is traced out from
// p0 to p1 via 0<= t <= 1

// start point
VECTOR3D_INIT(&(p->p0), p_init);

// end point
VECTOR3D_INIT(&(p->p1),p_term);

// now compute direction vector from p0->p1
VECTOR3D_Build(p_init, p_term, &(p->v));

} // end Init_Parm_Line3D

/////////////////////////////////////////////////////////////////

void Compute_Parm_Line2D(PARMLINE2D_PTR p, float t, POINT2D_PTR pt)
{
// this function computes the value of the sent parametric 
// line at the value of t

pt->x = p->p0.x + p->v.x*t;
pt->y = p->p0.y + p->v.y*t;

} // end Compute_Parm_Line2D

///////////////////////////////////////////////////////////////

void Compute_Parm_Line3D(PARMLINE3D_PTR p, float t, POINT3D_PTR pt)
{
// this function computes the value of the sent parametric 
// line at the value of t

pt->x = p->p0.x + p->v.x*t;
pt->y = p->p0.y + p->v.y*t;
pt->z = p->p0.z + p->v.z*t;

} // end Compute_Parm_Line3D

///////////////////////////////////////////////////////////////////

int Intersect_Parm_Lines2D(PARMLINE2D_PTR p1, PARMLINE2D_PTR p2, 
                           float *t1, float *t2)
{
// this function computes the interesection of the two parametric 
// line segments the function returns true if the segments 
// interesect and sets the values of t1 and t2 to the t values that 
// the intersection occurs on the lines p1 and p2 respectively, 
// however, the function may send back t value not in the range [0,1]
// this means that the segments don't intersect, but the lines that 
// they represent do, thus a retun of 0 means no intersection, a 
// 1 means intersection on the segments and a 2 means the lines 
// intersect, but not necessarily the segments and 3 means that 
// the lines are the same, thus they intersect everywhere

// basically we have a system of 2d linear equations, we need
// to solve for t1, t2 when x,y are both equal (if that's possible)

// step 1: test for parallel lines, if the direction vectors are 
// scalar multiples then the lines are parallel and can't possible
// intersect unless the lines overlap

float det_p1p2 = (p1->v.x*p2->v.y - p1->v.y*p2->v.x);
if (fabs(det_p1p2) <= EPSILON_E5)
   {
   // at this point, the lines either don't intersect at all
   // or they are the same lines, in which case they may intersect
   // at one or many points along the segments, at this point we 
   // will assume that the lines don't intersect at all, but later
   // we may need to rewrite this function and take into 
   // consideration the overlap and singular point exceptions
   return(PARM_LINE_NO_INTERSECT);

   } // end if

// step 2: now compute the t1, t2 values for intersection
// we have two lines of the form
// p    = p0    +  v*t, specifically
// p1   = p10   + v1*t1

// p1.x = p10.x + v1.x*t1
// p1.y = p10.y + v1.y*t1

// p2 = p20 + v2*t2
// p2   = p20   + v2*t2

// p2.x = p20.x + v2.x*t2
// p2.y = p20.y + v2.y*t2
// solve the system when x1 = x2 and y1 = y2
// explained in chapter 4
*t1 = (p2->v.x*(p1->p0.y - p2->p0.y) - p2->v.y*(p1->p0.x - p2->p0.x)) 
      /det_p1p2;

*t2 = (p1->v.x*(p1->p0.y - p2->p0.y) - p1->v.y*(p1->p0.x - p2->p0.x))
      /det_p1p2;

// test if the lines intersect on the segments
if ((*t1>=0) && (*t1<=1) && (*t2>=0) && (*t2<=1))
   return(PARM_LINE_INTERSECT_IN_SEGMENT);
else
   return(PARM_LINE_INTERSECT_OUT_SEGMENT);

} // end Intersect_Parm_Lines2D

///////////////////////////////////////////////////////////////

int Intersect_Parm_Lines2D(PARMLINE2D_PTR p1, PARMLINE2D_PTR p2, POINT2D_PTR pt)
{
// this function computes the interesection of the two 
// parametric line segments the function returns true if 
// the segments interesect and sets the values of pt to the 
// intersection point, however, the function may send back a 
// value not in the range [0,1] on the segments this means 
// that the segments don't intersect, but the lines that 
// they represent do, thus a retun of 0 means no intersection, 
// a 1 means intersection on the segments and a 2 means 
// the lines intersect, but not necessarily the segments

// basically we have a system of 2d linear equations, we need
// to solve for t1, t2 when x,y are both equal (if that's possible)

// step 1: test for parallel lines, if the direction vectors are 
// scalar multiples then the lines are parallel and can't possible
// intersect

float t1, t2, det_p1p2 = (p1->v.x*p2->v.y - p1->v.y*p2->v.x);

if (fabs(det_p1p2) <= EPSILON_E5)
   {
   // at this point, the lines either don't intersect at all
   // or they are the same lines, in which case they may intersect
   // at one or many points along the segments, at this point we 
   // will assume that the lines don't intersect at all, but later
   // we may need to rewrite this function and take into 
   // consideration the overlap and singular point exceptions
   return(PARM_LINE_NO_INTERSECT);

   } // end if

// step 2: now compute the t1, t2 values for intersection
// we have two lines of the form
// p    = p0    +  v*t, specifically
// p1   = p10   + v1*t1

// p1.x = p10.x + v1.x*t1
// p1.y = p10.y + v1.y*t1

// p2 = p20 + v2*t2
// p2   = p20   + v2*t2

// p2.x = p20.x + v2.x*t2
// p2.y = p20.y + v2.y*t2
// solve the system when x1 = x2 and y1 = y2
// explained in chapter 4
t1 = (p2->v.x*(p1->p0.y - p2->p0.y) - p2->v.y*(p1->p0.x - p2->p0.x))
     /det_p1p2;

t2 = (p1->v.x*(p1->p0.y - p2->p0.y) - p1->v.y*(p1->p0.x - p2->p0.x))
     /det_p1p2;

// now compute point of intersection
pt->x = p1->p0.x + p1->v.x*t1;
pt->y = p1->p0.y + p1->v.y*t1;

// test if the lines intersect on the segments
if ((t1>=0) && (t1<=1) && (t2>=0) && (t2<=1))
   return(PARM_LINE_INTERSECT_IN_SEGMENT);
else
   return(PARM_LINE_INTERSECT_OUT_SEGMENT);

} // end Intersect_Parm_Lines2D

///////////////////////////////////////////////////////////////

void PLANE3D_Init(PLANE3D_PTR plane, POINT3D_PTR p0, 
                  VECTOR3D_PTR normal, int normalize=0)
{
// this function initializes a 3d plane

// copy the point
POINT3D_COPY(&plane->p0, p0);

// if normalize is 1 then the normal is made into a unit vector
if (!normalize)
   VECTOR3D_COPY(&plane->n, normal);
else
   {
   // make normal into unit vector
   VECTOR3D_Normalize(normal,&plane->n);
   } // end else

} // end PLANE3D_Init

//////////////////////////////////////////////////////////////

 float Compute_Point_In_Plane3D(POINT3D_PTR pt, PLANE3D_PTR plane)
{
// test if the point in on the plane, in the positive halfspace
// or negative halfspace
float hs = plane->n.x*(pt->x - plane->p0.x) + 
           plane->n.y*(pt->y - plane->p0.y) +
           plane->n.z*(pt->z - plane->p0.z); 

// return half space value
return(hs);

} // end Compute_Point_In_Plane3D

///////////////////////////////////////////////////////////////

int Intersect_Parm_Line3D_Plane3D(PARMLINE3D_PTR pline, 
                                         PLANE3D_PTR plane, 
                                         float *t, POINT3D_PTR pt)
{
// this function determines where the sent parametric line 
// intersects the plane the function will project the line 
// infinitely in both directions, to compute the intersection, 
// but the line segment defined by p intersected the plane iff t e [0,1]
// also the function returns 0 for no intersection, 1 for 
// intersection of the segment and the plane and 2 for intersection 
// of the line along the segment and the plane 3, the line lies 
// in the plane

// first test of the line and the plane are parallel, if so 
// then they can't intersect unless the line lies in the plane!

float plane_dot_line = VECTOR3D_Dot(&pline->v, &plane->n);

if (fabs(plane_dot_line) <= EPSILON_E5)
   {
   // the line and plane are co-planer, does the line lie 
   // in the plane?
   if (fabs(Compute_Point_In_Plane3D(&pline->p0, plane)) <= EPSILON_E5)
      return(PARM_LINE_INTERSECT_EVERYWHERE);
   else
      return(PARM_LINE_NO_INTERSECT);
   } // end if

// from chapter 4 we know that we can solve for the t where 
// intersection occurs by
// a*(x0+vx*t) + b*(y0+vy*t) + c*(z0+vz*t) + d =0
// t = -(a*x0 + b*y0 + c*z0 + d)/(a*vx + b*vy + c*vz)
// x0,y0,z0, vx,vy,vz, define the line
// d = (-a*xp0 - b*yp0 - c*zp0), xp0, yp0, zp0, define the point on the plane 

*t = -(plane->n.x*pline->p0.x + 
       plane->n.y*pline->p0.y + 
       plane->n.z*pline->p0.z -
       plane->n.x*plane->p0.x - 
       plane->n.y*plane->p0.y - 
       plane->n.z*plane->p0.z) / (plane_dot_line);
   
// now plug t into the parametric line and solve for x,y,z
pt->x = pline->p0.x + pline->v.x*(*t);
pt->y = pline->p0.y + pline->v.y*(*t);
pt->z = pline->p0.z + pline->v.z*(*t);

// test interval of t
if (*t>=0.0 && *t<=1.0)
   return(PARM_LINE_INTERSECT_IN_SEGMENT );
else
   return(PARM_LINE_INTERSECT_OUT_SEGMENT);

} // end Intersect_Parm_Line3D_Plane3D

/////////////////////////////////////////////////////////////////


