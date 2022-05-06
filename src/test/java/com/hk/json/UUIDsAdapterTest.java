package com.hk.json;

import com.hk.Assets;
import junit.framework.TestCase;

import java.io.FileNotFoundException;
import java.util.*;

public class UUIDsAdapterTest extends TestCase
{
	public void test() throws FileNotFoundException
	{
		List<UUID> expected = Arrays.asList(
				UUID.fromString("37159149-a4c5-44b5-866d-6ac65502c200"),
				UUID.fromString("8d73434c-840b-4fab-a298-9c5e686b2a8b"),
				UUID.fromString("3bb348e6-8b40-41bc-8bc6-c18459c5a929"),
				UUID.fromString("908b1e6f-88e0-41e2-a804-df03944cda9b"),
				UUID.fromString("421a9627-b54c-48a8-9fcf-7280f508872e"),
				UUID.fromString("bb9e248e-de12-4e5b-8543-7fbd9c6d8947"),
				UUID.fromString("a0d2066e-6d9b-44b9-8621-fec367a99bd5"),
				UUID.fromString("073f895d-4e90-4da6-a462-7b1da3f7bb2d"),
				UUID.fromString("f5d7cf52-da91-4934-a061-c28e9adb2e61"),
				UUID.fromString("f35d0dc8-1f35-4ff8-8a44-a577c312f9f7"),
				UUID.fromString("a22d0709-eb1c-4e6e-abd4-86032c7b441f"),
				UUID.fromString("aa70abfd-8af4-4a07-9429-f9b92da30963"),
				UUID.fromString("2750f340-ba4b-450e-9a19-0a3ac91870dd"),
				UUID.fromString("b89f0658-cf8d-4a36-b84d-e961bebd624a"),
				UUID.fromString("8aae4ca2-fa4d-4885-a873-e1f36d79253b"),
				UUID.fromString("3ba883e5-79ff-498b-9d7f-edba68fb3188"),
				UUID.fromString("bdfe1189-2a0f-43c0-b612-2606a30238a5"),
				UUID.fromString("b2b3b4a4-7258-4ca7-8214-e71c4ba173d3"),
				UUID.fromString("fcfc7bc4-12a0-4902-aba3-f403eb5356aa"),
				UUID.fromString("28ae48d7-d954-46cd-a4bc-4fba32fc9c10"),
				UUID.fromString("2cdf2c43-a500-498a-b841-55a4b16cac09"),
				UUID.fromString("672a7396-9c01-49e1-ac69-8a9d8948ea5d"),
				UUID.fromString("4651bfa8-143b-47bd-8690-027859cd4c45"),
				UUID.fromString("2b52d9cd-4ac8-4cda-84fb-125e9a99371f"),
				UUID.fromString("6b3879b9-8775-478b-a646-2e2239741345"),
				UUID.fromString("14c3c2d4-45b1-4f7c-92a3-4cfbf88a107a"),
				UUID.fromString("57393834-1ba6-4c7b-b787-5c41a4346795"),
				UUID.fromString("492b2f47-826d-4f53-89be-c8ce753afa83"),
				UUID.fromString("ebaa537d-522e-4581-986d-135a0a0463e7"),
				UUID.fromString("4c2899b6-87e8-4d7a-b43d-e2dbed6ca48a"),
				UUID.fromString("cf27f81c-a60a-4961-8fbb-8c68869a1bb5"),
				UUID.fromString("f6e2a0a3-d3d5-40ce-b9a9-06900d735210"),
				UUID.fromString("be951369-7f66-4a0a-aec3-9d5ee6d1826d"),
				UUID.fromString("443b1f2a-1cfa-4508-9039-689a4c4d7480"),
				UUID.fromString("891306d1-56ca-4546-9947-374d06c3f45c"),
				UUID.fromString("263c9c14-15f3-49f3-8c6e-b8286261b561"),
				UUID.fromString("8ab2dd00-32ad-4945-bff2-cd7a15f00b1f"),
				UUID.fromString("6e33e7c4-39d0-4f86-b8ac-4c1b6c138c9f"),
				UUID.fromString("a11eb858-a4a7-4ac5-aefd-f4b0466d8e20"),
				UUID.fromString("b2b3719f-735b-47f2-b331-60f000be4b9b"),
				UUID.fromString("9c096538-750f-4fff-b0e6-44fe25192d66"),
				UUID.fromString("d3eca51c-4b99-42cd-bc40-005d64b64788"),
				UUID.fromString("8f1324cc-2463-4552-a6d7-5906c88ca856"),
				UUID.fromString("08343c48-06dd-446e-b302-ec5189872130"),
				UUID.fromString("475e98c2-2356-479d-b255-f802c1476983"),
				UUID.fromString("3e534b78-bd5e-4188-9a07-bd7a8ad5d75a"),
				UUID.fromString("f87807fd-1a8c-4858-8ee7-6409bf50a042"),
				UUID.fromString("01ea1098-f44d-4e32-8ae0-0eddd3858ace"),
				UUID.fromString("d6f99e8d-9221-4d85-852e-75ad5d351d1e"),
				UUID.fromString("0b32f3d0-1418-4307-afbc-076958180af1"),
				UUID.fromString("ffb78f20-4425-45f9-b66e-2adc03e0b26c"),
				UUID.fromString("c1c432bb-9f96-4316-a6ad-a14c9b68d4a2"),
				UUID.fromString("c2a5ce48-0278-4f14-ad77-0d040e32710d"),
				UUID.fromString("9891315a-1e74-45da-afde-23e06f9540e5"),
				UUID.fromString("d895cb73-538a-4079-bcaf-68b0564ef055"),
				UUID.fromString("afc90b4d-9ebb-4b36-acaa-d280f467bcf6"),
				UUID.fromString("97bae14b-049e-4232-8c4f-10dc2c1283b6"),
				UUID.fromString("abd6a471-787d-4456-b343-f6543f5dc30c"),
				UUID.fromString("1eb2f676-d450-4835-be70-9526367f6c8e"),
				UUID.fromString("393e20de-ce22-4d2f-90bf-1c9044a26201"),
				UUID.fromString("1384589d-063e-4665-a136-a9634783fb90"),
				UUID.fromString("c62ca9a9-e302-4545-adbf-ab14980a64ab"),
				UUID.fromString("c9d98b95-e865-43b3-89a5-d219dfb49c64"),
				UUID.fromString("4374c86a-1c24-40b0-8d07-b5ec39aab9ea"),
				UUID.fromString("873d4ba1-6ce8-4505-a96f-913c9df6e0f9"),
				UUID.fromString("42c16e9d-ab1c-47cc-9874-058630fde48b"),
				UUID.fromString("d76c2afe-5996-4b6a-9ed5-eae27e34af61"),
				UUID.fromString("edd85d5e-26e6-4bc1-90cd-bf4bfb6c1ca6"),
				UUID.fromString("a2a84bb1-6ec6-4d0d-af64-812be30ae885"),
				UUID.fromString("dcfdcf87-62f1-4d63-8389-75d7c478be34"),
				UUID.fromString("9e47fee2-9be1-499f-9734-b81fce72fc2f"),
				UUID.fromString("45652552-704d-48d1-834f-ae9c4890345b"),
				UUID.fromString("2d172a42-0cb7-461c-a732-34322f6dd273"),
				UUID.fromString("1e99985d-2afb-4437-95f7-8074a88d4054"),
				UUID.fromString("051598ff-1d80-4c15-b9ea-eb54d6aa8ce3"),
				UUID.fromString("cb56ebc0-9cce-43d5-bb5b-31d2d8c40585"),
				UUID.fromString("e0d1f053-79aa-47ab-8705-08883976f61a"),
				UUID.fromString("01a75e6f-6307-4a95-9ea0-6c9c5bc4abb5"),
				UUID.fromString("21c119ca-8bb0-4772-9adf-8c3b854f7003"),
				UUID.fromString("b327e720-6ea9-40b0-854d-123fad3e5535"),
				UUID.fromString("899cc959-67ae-4710-a68c-ebcfb83f0472"),
				UUID.fromString("a75714c0-3a92-4078-a1b6-b9b5990cb932"),
				UUID.fromString("d71b3f90-2b32-4373-b419-22e7276fe40e"),
				UUID.fromString("a306b702-cf44-46b7-996d-5d4d5a52ba9f"),
				UUID.fromString("ac2fcddc-a870-47b9-adb5-3ad0ce982ef4"),
				UUID.fromString("580860a1-c4d2-4132-8874-3fc6760c4cb7"),
				UUID.fromString("5257bbc7-53dd-46d0-804f-691bd8dd2673"),
				UUID.fromString("e47d86f2-1983-4d84-95ec-c6190500c24b"),
				UUID.fromString("87542f4a-de12-4b59-a621-b319a0757e09"),
				UUID.fromString("40d4468b-a24b-40d2-89cc-e410579d00ca"),
				UUID.fromString("37a6beb8-941b-410e-a4f8-2cde724411b2"),
				UUID.fromString("454997c6-d012-4411-8568-e6befc759dbb"),
				UUID.fromString("12f3ec1a-7775-4e48-b072-0a996bd88543"),
				UUID.fromString("2207484c-c2cf-45fd-8b91-8c2eb15ef8c5"),
				UUID.fromString("9286c696-f21c-4073-9f41-51ebc2bac5ce"),
				UUID.fromString("81fb8904-a1e7-4dde-903a-74f4c44c4446"),
				UUID.fromString("59812290-0c8c-45a1-b8ec-874b08827f13"),
				UUID.fromString("a549b64f-5333-4581-8c9d-b65d18ca7b29"),
				UUID.fromString("18da38ea-5fcf-4b98-9485-a9be7d110262"),
				UUID.fromString("371210c6-198e-4be7-aa02-49e6b3d730e7"));

		FunctionAdapter<UUID> adapter = new FunctionAdapter<>(UUID.class)
				.setFromJson(value -> UUID.fromString(value.getString()))
				.setToJson(uuid -> new JsonString(uuid.toString()));

		JsonValue value = Json.read(Assets.get("json/uuids.json"));

		assertNotNull(value);
		assertTrue(value.isArray());
		List<UUID> lst = value.getArray().toList(adapter);

		assertEquals(expected, lst);

		UUID uuid = UUID.randomUUID();
		String json = "{ \"uuid\": \"" + uuid + "\" }";
		assertEquals(uuid, Json.read(json).getObject().get("uuid").get(adapter));
	}
}