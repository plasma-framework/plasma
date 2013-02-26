/**
 *         PlasmaSDO™ License
 * 
 * This is a community release of PlasmaSDO™, a dual-license 
 * Service Data Object (SDO) 2.1 implementation. 
 * This particular copy of the software is released under the 
 * version 2 of the GNU General Public License. PlasmaSDO™ was developed by 
 * TerraMeta Software, Inc.
 * 
 * Copyright (c) 2013, TerraMeta Software, Inc. All rights reserved.
 * 
 * General License information can be found below.
 * 
 * This distribution may include materials developed by third
 * parties. For license and attribution notices for these
 * materials, please refer to the documentation that accompanies
 * this distribution (see the "Licenses for Third-Party Components"
 * appendix) or view the online documentation at 
 * <http://plasma-sdo.org/licenses/>.
 *  
 */
package org.plasma.common.exception;
                                                                                        

                                                                       
public interface ErrorConstants  
{                                    
    public static String ERROR_SEVERITY_INFO                = "error_severity_info";
    public static String ERROR_SEVERITY_WARNING               = "error_severity_warning";                
    public static String ERROR_SEVERITY_FATAL                 = "error_severity_fatal";                  
    public static String ERROR_TYPE_INTERNAL                  = "error_type_internal";                   
    public static String ERROR_TYPE_USER_INPUT                = "error_type_user_input";                
    public static String ERROR_TYPE_USER_CONCURRENCY          = "error_type_user_concurrency";           
    public static String ERROR_TYPE_USER_SESSION              = "error_type_user_session";           
                                                                           
    public static String ERROR_MESSAGE_INTERNAL               = "error_message_internal";                
    public static String ERROR_MESSAGE_MAX_WILDCARDS_EXCEEDED = "error_message_max_wildcards_exceeded";  
    public static String ERROR_MESSAGE_MAX_RESULTS_EXCEEDED   = "error_message_max_results_exceeded";    
    public static String ERROR_MESSAGE_NO_RESULTS_FOUND         = "error_message_no_results_found";    
    public static String ERROR_MESSAGE_NO_CRITERIA_ENTERED   = "error_message_no_criteria_entered";    
    public static String ERROR_MESSAGE_INVALID_DATE_FORMAT    = "error_message_invalid_date_format";     
    public static String ERROR_MESSAGE_INVALID_DATE_FORMAT2    = "error_message_invalid_date_format2";     
    public static String ERROR_MESSAGE_INVALID_NUMBER_FORMAT    = "error_message_invalid_number_format";     
    public static String ERROR_MESSAGE_INVALID_SNAPSHOT       = "error_message_invalid_snapshot";        
    public static String ERROR_MESSAGE_RECORD_LOCKED          = "error_message_record_locked";           
    public static String ERROR_MESSAGE_SESSION_TIMEOUT          = "error_message_session_timeout";  
    public static String ERROR_MESSAGE_USER_ROLE_NOT_FOUND      = "error_message_user_role_not_found";               
    public static String ERROR_MESSAGE_PPSO_NOT_FOUND      = "error_message_ppso_not_found";                                                           
    public static String ERROR_MESSAGE_CHANNEL_NOT_FOUND      = "error_message_channel_not_found";
}                                                                                      