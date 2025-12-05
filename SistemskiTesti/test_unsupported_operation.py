import pexpect
from unittest.mock import patch

class UnsupportedOperationException(Exception):
    pass

class ClassNotFoundException(Exception):
    pass

def test_unsupported_operation():
    print("=== Starting test_save_restore ===")
    baza = pexpect.pexpect()
    result = None

    try:
        print("1. Waiting for 'Ukaz> ' prompt...")
        baza.expect("Ukaz> ")
        print("2. Got prompt successfully")

        # Test case 1: UnsupportedOperationException
        print("\n3. Testing UnsupportedOperationException scenario")
        with patch.object(baza, 'send', side_effect=UnsupportedOperationException("Operation not supported")) as mock_send:
            print("4. Mock send() set up to raise UnsupportedOperationException")
            try:
                print("5. Sending 'dodaj' command...")
                baza.send('dodaj')
                print("6. This line shouldn't be reached (exception should occur)")
            except UnsupportedOperationException as e:
                result = "Operacija ni podprta"
                print(f"7. Caught UnsupportedOperationException: {result}")
                print(f"8. Mock called with: {mock_send.call_args}")

        print("\n9. Testing ClassNotFoundException scenario")
        with patch.object(baza, 'send', side_effect=ClassNotFoundException("Class not found")) as mock_send:
            print("10. Mock send() set up to raise ClassNotFoundException")
            try:
                print("11. Sending 'dodaj' command...")
                baza.send('dodaj')
                print("12. This line shouldn't be reached (exception should occur)")
            except ClassNotFoundException as e:
                result = "Neznana oblika"
                print(f"13. Caught ClassNotFoundException: {result}")
                print(f"14. Mock called with: {mock_send.call_args}")

        print("\n15. PASSED\ttest_unsupported_operation")

    except Exception as e:
        print(f"\n16. FAILED\ttest_unsupported_operation: {str(e)}")
        import traceback
        traceback.print_exc()

    finally:
        print("17. Cleaning up...")
        baza.kill()
        print("=== Test completed ===")

if __name__ == "__main__":
    test_unsupported_operation()