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
package org.onesocialweb.smack;

import java.util.ArrayList;
import java.util.List;

import org.onesocialweb.client.Inbox;
import org.onesocialweb.client.InboxEventHandler;
import org.onesocialweb.client.exception.AuthenticationRequired;
import org.onesocialweb.client.exception.ConnectionRequired;
import org.onesocialweb.client.exception.RequestException;
import org.onesocialweb.model.activity.ActivityEntry;

public class InboxImp implements Inbox {

	private final List<InboxEventHandler> handlers = new ArrayList<InboxEventHandler>();

	private final OswServiceImp service;

	private List<ActivityEntry> entries = new ArrayList<ActivityEntry>();
	
	public InboxImp(OswServiceImp service) {
		this.service = service;
	}
	
	@Override
	public List<ActivityEntry> getEntries() {
		return entries;
	}

	@Override
	public boolean refresh() {
		try {
			return service.refreshInbox();
		} catch (ConnectionRequired e) {

		} catch (AuthenticationRequired e) {

		} catch (RequestException e) {

		}
		return false;
	}

	@Override
	public void registerInboxEventHandler(InboxEventHandler handler) {
		handlers.add(handler);
	}

	@Override
	public void unregisterInboxEventHandler(InboxEventHandler handler) {
		handlers.remove(handler);
	}

	@Override
	public void addEntry(ActivityEntry entry) {
		entries.add(0, entry);
		notifyMessageReceived(entry);
	}

	@Override
	public void removeEntry(ActivityEntry entry) {
		entries.remove(entry);
		notifyMessageDeleted(entry);
	}

	@Override
	public void setEntries(List<ActivityEntry> entries) {
		this.entries = entries;
		notifyRefresh(entries);
	}
	
	private void notifyMessageReceived(ActivityEntry activity) {
		for (InboxEventHandler handler : handlers) {
			handler.onMessageReceived(activity);
		}
	}
	
	private void notifyMessageDeleted(ActivityEntry activity) {
		for (InboxEventHandler handler : handlers) {
			handler.onMessageDeleted(activity);
		}
	}
	
	private void notifyRefresh(List<ActivityEntry> activities) {
		for (InboxEventHandler handler : handlers) {
			handler.onRefresh(activities);
		}
	}

	@Override
	public int getSize() {
		if (entries != null) {
			return entries.size();
		} else {
			return 0;
		}
	}

}
