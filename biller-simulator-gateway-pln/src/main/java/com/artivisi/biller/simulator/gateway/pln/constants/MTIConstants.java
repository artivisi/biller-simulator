/**
 * Copyright (C) 2011 ArtiVisi Intermedia <info@artivisi.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.artivisi.biller.simulator.gateway.pln.constants;

public abstract class MTIConstants {
	public static final String NETWORK_MANAGEMENT_REQUEST = "0800";
	public static final String NETWORK_MANAGEMENT_RESPONSE = "0810";
	public static final String INQUIRY_REQUEST = "0100";
	public static final String INQUIRY_RESPONSE = "0110";
	public static final String PAYMENT_REQUEST = "0200";
	public static final String PAYMENT_RESPONSE = "0210";
	public static final String REVERSAL_REQUEST = "0400";
	public static final String REVERSAL_RESPONSE = "0410";
	public static final String REVERSAL_REPEAT_REQUEST = "0401";
	public static final String REVERSAL_REPEAT_RESPONSE = "0411";
}
