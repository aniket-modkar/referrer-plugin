import Foundation

@objc public class Referrer: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
