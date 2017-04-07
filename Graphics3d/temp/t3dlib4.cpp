





















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


