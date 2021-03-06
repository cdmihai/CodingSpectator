/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.core.internal.resources;

import java.io.DataInputStream;
import java.io.IOException;
import org.eclipse.core.internal.utils.ObjectMap;
import org.eclipse.core.runtime.*;

public class SyncInfoSnapReader_3 extends SyncInfoSnapReader {

	public SyncInfoSnapReader_3(Workspace workspace, Synchronizer synchronizer) {
		super(workspace, synchronizer);
	}

	private ObjectMap internalReadSyncInfo(DataInputStream input) throws IOException {
		int size= input.readInt();
		ObjectMap map= new ObjectMap(size);
		for (int i= 0; i < size; i++) {
			// read the qualified name
			String qualifier= input.readUTF();
			String local= input.readUTF();
			QualifiedName name= new QualifiedName(qualifier, local);
			// read the bytes
			int length= input.readInt();
			byte[] bytes= new byte[length];
			input.readFully(bytes);
			// put them in the table
			map.put(name, bytes);
		}
		return map;
	}

	/**
	 * SNAP_FILE -> [VERSION_ID RESOURCE]* VERSION_ID -> int RESOURCE -> RESOURCE_PATH SIZE
	 * SYNCINFO* RESOURCE_PATH -> String SIZE -> int SYNCINFO -> QNAME BYTES QNAME -> String String
	 * BYTES -> byte[]
	 */
	public void readSyncInfo(DataInputStream input) throws IOException {
		IPath path= new Path(input.readUTF());
		ObjectMap map= internalReadSyncInfo(input);
		// set the table on the resource info
		ResourceInfo info= workspace.getResourceInfo(path, true, false);
		if (info == null)
			return;
		info.setSyncInfo(map);
		info.clear(ICoreConstants.M_SYNCINFO_SNAP_DIRTY);
	}
}
