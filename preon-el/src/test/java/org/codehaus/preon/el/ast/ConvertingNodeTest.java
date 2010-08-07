/**
 * Copyright (C) 2009-2010 Wilfred Springer
 *
 * This file is part of Preon.
 *
 * Preon is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2, or (at your option) any later version.
 *
 * Preon is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Preon; see the file COPYING. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Linking this library statically or dynamically with other modules is making a
 * combined work based on this library. Thus, the terms and conditions of the
 * GNU General Public License cover the whole combination.
 *
 * As a special exception, the copyright holders of this library give you
 * permission to link this library with independent modules to produce an
 * executable, regardless of the license terms of these independent modules, and
 * to copy and distribute the resulting executable under terms of your choice,
 * provided that you also meet, for each linked independent module, the terms
 * and conditions of the license of that module. An independent module is a
 * module which is not derived from or based on this library. If you modify this
 * library, you may extend this exception to your version of the library, but
 * you are not obligated to do so. If you do not wish to do so, delete this
 * exception statement from your version.
 */
package org.codehaus.preon.el.ast;

import junit.framework.TestCase;
import org.codehaus.preon.el.ast.ConvertingNode;
import org.codehaus.preon.el.ast.Node;

import static org.easymock.EasyMock.*;

/**
 * A number of tests for the {@link ConvertingNode}.
 * 
 * @author Wilfred Springer (wis)
 * 
 */
public class ConvertingNodeTest extends TestCase {

    /**
     * The source node.
     */
    private Node source;

    /*
     * (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp() {
        source = createMock(Node.class);
    }

    /**
     * Tests a conversion from String to Integer.
     */
    public void testNonConvertingInteger() {
        expect(source.getType()).andReturn(String.class);
        replay(source);
        Node result = ConvertingNode.tryConversionToIntegerNode(source);
        assertFalse(result instanceof ConvertingNode);
        verify(source);
    }

    /**
     * Tests a conversion from Byte to Integer.
     */
    public void testConvertingInteger() {
        Object context = new Object();
        expect(source.getType()).andReturn(Byte.class).times(2);
        expect(source.eval(context)).andReturn(new Byte((byte) 3));
        replay(source);
        Node result = ConvertingNode.tryConversionToIntegerNode(source);
        assertTrue(result instanceof ConvertingNode);
        assertEquals(Integer.class, result.getType());
        Object value = result.eval(context);
        assertEquals(Integer.class, value.getClass());
        assertEquals(3, ((Integer) value).intValue());
        assertNotNull(value);
        verify(source);
    }

}