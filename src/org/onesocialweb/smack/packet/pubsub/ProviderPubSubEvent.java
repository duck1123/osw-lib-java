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

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smack.provider.PacketExtensionProvider;
import org.onesocialweb.model.activity.ActivityEntry;
import org.onesocialweb.xml.xpp.imp.DefaultXppActivityReader;
import org.xmlpull.v1.XmlPullParser;

public class ProviderPubSubEvent implements PacketExtensionProvider {

	@Override
	public PacketExtension parseExtension(XmlPullParser parser) throws Exception {
		final DefaultXppActivityReader reader = new DefaultXppActivityReader();
		final List<ActivityEntry> entries = new ArrayList<ActivityEntry>();

		boolean done = false;
		String node = new String();

		while (!done) {
			int eventType = parser.next();
			if (eventType == XmlPullParser.START_TAG) {
				if (parser.getName().equals("items")) {
					node = parser.getAttributeValue(null, "node");
				} else if (parser.getName().equals("entry")) {
					entries.add(reader.parse(parser));
				}
			} else if (eventType == XmlPullParser.END_TAG) {
				if (parser.getName().equals("event")) {
					done = true;
				}
			}
		}

		MessagePubSubEvent message = new MessagePubSubEvent(node);
		message.setEntries(entries);
		
		return message;

	}
}
