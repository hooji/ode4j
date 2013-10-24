package org.ode4j.tests.libccd;

import static org.junit.Assert.*;
import static org.ode4j.ode.internal.cpp4j.Cstdio.*;
import static org.ode4j.ode.internal.libccd.CCD.*;
import static org.ode4j.ode.internal.libccd.CCDMPR.*;
import static org.ode4j.ode.internal.libccd.CCDQuat.*;
import static org.ode4j.ode.internal.libccd.CCDVec3.*;
import static org.ode4j.tests.libccd.CCDTestCommon.*;
import static org.ode4j.tests.libccd.CCDTestSupport.*;

import org.junit.Test;
import org.ode4j.ode.internal.cpp4j.java.RefDouble;

public class TestMPRBoxCyl {

//	#define TOSVT() \
//	    svtObjPen(box, cyl, stdout, "Pen 1", depth, dir, pos); \
//	    ccdVec3Scale(dir, depth); \
//	    ccdVec3Add(cyl.pos, dir); \
//	    svtObjPen(box, cyl, stdout, "Pen 1", depth, dir, pos)

	@Test
	public void mprBoxcylIntersect()
	{
	    ccd_t ccd = new ccd_t();
	    ccd_box_t box = CCD_BOX();
	    ccd_cyl_t cyl = CCD_CYL();
	    int res;
	    ccd_vec3_t axis = new ccd_vec3_t();

	    box.x = 0.5;
	    box.y = 1.;
	    box.z = 1.5;
	    cyl.radius = 0.4;
	    cyl.height = 0.7;

	    CCD_INIT(ccd);
	    ccd.support1 = ccdSupport;
	    ccd.support2 = ccdSupport;
	    ccd.center1  = ccdObjCenter;
	    ccd.center2  = ccdObjCenter;

	    ccdVec3Set(cyl.pos, 0.1, 0., 0.);
	    res = ccdMPRIntersect(box, cyl, ccd);
	    assertTrue(res!=0);

	    ccdVec3Set(cyl.pos, .6, 0., 0.);
	    res = ccdMPRIntersect(box, cyl, ccd);
	    assertTrue(res!=0);

	    ccdVec3Set(cyl.pos, .6, 0.6, 0.);
	    res = ccdMPRIntersect(box, cyl, ccd);
	    assertTrue(res!=0);

	    ccdVec3Set(cyl.pos, .6, 0.6, 0.5);
	    res = ccdMPRIntersect(box, cyl, ccd);
	    assertTrue(res!=0);

	    ccdVec3Set(axis, 0., 1., 0.);
	    ccdQuatSetAngleAxis(cyl.quat, M_PI / 3., axis);
	    ccdVec3Set(cyl.pos, .6, 0.6, 0.5);
	    res = ccdMPRIntersect(box, cyl, ccd);
	    assertTrue(res!=0);

	    ccdVec3Set(axis, 0.67, 1.1, 0.12);
	    ccdQuatSetAngleAxis(cyl.quat, M_PI / 4., axis);
	    ccdVec3Set(cyl.pos, .6, 0., 0.5);
	    res = ccdMPRIntersect(box, cyl, ccd);
	    assertTrue(res!=0);

	    ccdVec3Set(axis, -0.1, 2.2, -1.);
	    ccdQuatSetAngleAxis(cyl.quat, M_PI / 5., axis);
	    ccdVec3Set(cyl.pos, .6, 0., 0.5);
	    ccdVec3Set(axis, 1., 1., 0.);
	    ccdQuatSetAngleAxis(box.quat, -M_PI / 4., axis);
	    ccdVec3Set(box.pos, .6, 0., 0.5);
	    res = ccdMPRIntersect(box, cyl, ccd);
	    assertTrue(res!=0);

	    ccdVec3Set(axis, -0.1, 2.2, -1.);
	    ccdQuatSetAngleAxis(cyl.quat, M_PI / 5., axis);
	    ccdVec3Set(cyl.pos, .6, 0., 0.5);
	    ccdVec3Set(axis, 1., 1., 0.);
	    ccdQuatSetAngleAxis(box.quat, -M_PI / 4., axis);
	    ccdVec3Set(box.pos, .9, 0.8, 0.5);
	    res = ccdMPRIntersect(box, cyl, ccd);
	    assertTrue(res!=0);
	}



	@Test
	public void mprBoxcylPen()
	{
	    ccd_t ccd = new ccd_t();
	    ccd_box_t box = CCD_BOX();
	    ccd_cyl_t cyl = CCD_CYL();
	    int res;
	    ccd_vec3_t axis = new ccd_vec3_t();
	    RefDouble depth = new RefDouble();
	    ccd_vec3_t dir = new ccd_vec3_t(), pos = new ccd_vec3_t();

	    box.x = 0.5;
	    box.y = 1.;
	    box.z = 1.5;
	    cyl.radius = 0.4;
	    cyl.height = 0.7;

	    CCD_INIT(ccd);
	    ccd.support1 = ccdSupport;
	    ccd.support2 = ccdSupport;
	    ccd.center1  = ccdObjCenter;
	    ccd.center2  = ccdObjCenter;

	    ccdVec3Set(cyl.pos, 0.1, 0., 0.);
	    res = ccdMPRPenetration(box, cyl, ccd, depth, dir, pos);
	    assertTrue(res == 0);
	    recPen(depth, dir, pos, stdout, "Pen 1");
	    //TOSVT();

	    ccdVec3Set(cyl.pos, .6, 0., 0.);
	    res = ccdMPRPenetration(box, cyl, ccd, depth, dir, pos);
	    assertTrue(res == 0);
	    recPen(depth, dir, pos, stdout, "Pen 2");
	    //TOSVT();

	    ccdVec3Set(cyl.pos, .6, 0.6, 0.);
	    res = ccdMPRPenetration(box, cyl, ccd, depth, dir, pos);
	    assertTrue(res == 0);
	    recPen(depth, dir, pos, stdout, "Pen 3");
	    //TOSVT();

	    ccdVec3Set(cyl.pos, .6, 0.6, 0.5);
	    res = ccdMPRPenetration(box, cyl, ccd, depth, dir, pos);
	    assertTrue(res == 0);
	    recPen(depth, dir, pos, stdout, "Pen 4");
	    //TOSVT();

	    ccdVec3Set(axis, 0., 1., 0.);
	    ccdQuatSetAngleAxis(cyl.quat, M_PI / 3., axis);
	    ccdVec3Set(cyl.pos, .6, 0.6, 0.5);
	    res = ccdMPRPenetration(box, cyl, ccd, depth, dir, pos);
	    assertTrue(res == 0);
	    recPen(depth, dir, pos, stdout, "Pen 5");
	    //TOSVT();

	    ccdVec3Set(axis, 0.67, 1.1, 0.12);
	    ccdQuatSetAngleAxis(cyl.quat, M_PI / 4., axis);
	    ccdVec3Set(cyl.pos, .6, 0., 0.5);
	    res = ccdMPRPenetration(box, cyl, ccd, depth, dir, pos);
	    assertTrue(res == 0);
	    recPen(depth, dir, pos, stdout, "Pen 6");
	    //TOSVT();

	    ccdVec3Set(axis, -0.1, 2.2, -1.);
	    ccdQuatSetAngleAxis(cyl.quat, M_PI / 5., axis);
	    ccdVec3Set(cyl.pos, .6, 0., 0.5);
	    ccdVec3Set(axis, 1., 1., 0.);
	    ccdQuatSetAngleAxis(box.quat, -M_PI / 4., axis);
	    ccdVec3Set(box.pos, .6, 0., 0.5);
	    res = ccdMPRPenetration(box, cyl, ccd, depth, dir, pos);
	    assertTrue(res == 0);
	    recPen(depth, dir, pos, stdout, "Pen 7");
	    //TOSVT();

	    ccdVec3Set(axis, -0.1, 2.2, -1.);
	    ccdQuatSetAngleAxis(cyl.quat, M_PI / 5., axis);
	    ccdVec3Set(cyl.pos, .6, 0., 0.5);
	    ccdVec3Set(axis, 1., 1., 0.);
	    ccdQuatSetAngleAxis(box.quat, -M_PI / 4., axis);
	    ccdVec3Set(box.pos, .9, 0.8, 0.5);
	    res = ccdMPRPenetration(box, cyl, ccd, depth, dir, pos);
	    assertTrue(res == 0);
	    recPen(depth, dir, pos, stdout, "Pen 8");
	    //TOSVT();
	}


}
