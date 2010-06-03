/*
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package net.rrm.ehour.service.mail.callbacks;

import net.rrm.ehour.domain.MailLogAssignment;
import net.rrm.ehour.service.mail.dto.AssignmentPMMessage;
import net.rrm.ehour.service.mail.dto.MailTaskMessage;

import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;

/**
 * Callback 
 **/
@Component
public class AssignmentMsgCallback extends MailTaskCallback
{
	/* (non-Javadoc)
	 * @see net.rrm.ehour.service.mail.callbacks.MailTaskCallback#failure(org.springframework.mail.MailException)
	 */
	public void mailTaskFailure(MailTaskMessage mailTaskMessage, MailException me)
	{
		persistMailLogAssignmentMessage(mailTaskMessage, false, me.getMessage());
	}

	/* (non-Javadoc)
	 * @see net.rrm.ehour.service.mail.callbacks.MailTaskCallback#success()
	 */
	public void mailTaskSuccess(MailTaskMessage mailTaskMessage)
	{
		persistMailLogAssignmentMessage(mailTaskMessage, true, null);
	}

	/**
	 * Persist mail log assignment message
	 * @param mailTaskMessage
	 * @param success
	 * @param resultMsg
	 */
	private void persistMailLogAssignmentMessage(MailTaskMessage mailTaskMessage, boolean success, String resultMsg)
	{
		MailLogAssignment 	mailLog;
		AssignmentPMMessage asgMessage = (AssignmentPMMessage)mailTaskMessage;
		
		mailLog = new MailLogAssignment();
		mailLog.setProjectAssignment(asgMessage.getAggregate().getProjectAssignment());
		mailLog.setBookDate(asgMessage.getBookDate());
		mailLog.setBookedHours(new Float(asgMessage.getAggregate().getHours().floatValue()));
		
		persistMailMessage(asgMessage, success, resultMsg, mailLog);
	}
}