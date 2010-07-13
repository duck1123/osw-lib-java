/*
 *  Copyright 2010 Vodafone Group Services Ltd.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *    
 */
package org.onesocialweb.smack.packet.pubsub;

import java.util.List;

import org.jivesoftware.smack.packet.IQ;
import org.onesocialweb.model.activity.ActivityEntry;

public class IQPubSubItems extends IQ {

	private final String node;

	private List<ActivityEntry> entries;

	public IQPubSubItems(String node) {
		this.node = node;
	}

	public List<ActivityEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<ActivityEntry> entries) {
		this.entries = entries;
	}

	@Override
	public String getChildElementXML() {
		final StringBuffer buf = new StringBuffer();
		buf.append("<pubsub xmlns='http://jabber.org/protocol/pubsub'>");
		buf.append("<items node='" + node + "' />");
		buf.append("</pubsub>");
		return buf.toString();
	}
}
