/*
 * Copyright 2011-17 Fraunhofer ISE
 *
 * This file is part of jASN1.
 * For more information visit http://www.openmuc.org
 *
 * jASN1 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jASN1 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jASN1.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.openmuc.jasn1.compiler.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AsnModule {

    public enum TagDefault {
        EXPLICIT,
        IMPLICIT,
        AUTOMATIC;
    }

    public ArrayList asnValues;
    public ArrayList exportSymbolList;
    public boolean exported;
    public boolean extensible;
    public List<SymbolsFromModule> importSymbolFromModuleList = new ArrayList<>();
    ArrayList importSymbolList;
    public boolean imported;
    public AsnModuleIdentifier moduleIdentifier;
    public boolean tag;
    public TagDefault tagDefault = TagDefault.EXPLICIT;
    public final HashMap<String, AsnType> typesByName = new HashMap<>();

    public AsnModule() {
        exportSymbolList = new ArrayList();
        importSymbolList = new ArrayList();
        asnValues = new ArrayList();
    }
}
