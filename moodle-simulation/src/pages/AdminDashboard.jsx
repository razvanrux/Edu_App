import React, { useState, useEffect } from "react";
import axios from "axios";
import "../styles/admin.css";
import API_CONFIG from "../api/config.js";

function AdminDashboard() {
  const [isOpen, setIsOpen] = useState(false);
  const [isOpen2, setIsOpen2] = useState(false);
  const [isOpen3, setIsOpen3] = useState(false);
  const [visible, setVisible] = useState("");
  const [visible2, setVisible2] = useState("");
  const [visible3, setVisible3] = useState("");
  const [action, setAction] = useState(""); // Store the current action (add, change, delete)
  const [userType, setUserType] = useState(""); // Store the selected user type (students, professors)
  const [data, setData] = useState([]); // Store fetched data
  const [error, setError] = useState(""); // Error message
  const [selectedUser, setSelectedUser] = useState(null);

  const toggleBox = (selectedAction) => {
    resetUrl();
    setAction(selectedAction); // Set the action to determine the route
    if (!isOpen) {
      setIsOpen(true);
      setVisible2("");
      setIsOpen2(false)
      setVisible3("");
      setIsOpen3(false)
      setVisible("visible");
    } else {
      setIsOpen(false);
      setVisible("");
    }
  };

  const toggleSecondBox = (selectedUserType) => {
    setUserType(selectedUserType); // Set the user type
    if (!isOpen2) {
      setIsOpen2(true);
      setVisible2("visible");
    }
  };
   const toggleThirdBox = (user) => {
    setSelectedUser(user); // Set the selected user
    if (!isOpen3) {
      setIsOpen3(true);
      setVisible3("visible");
    }
    console.log(user, selectedUser)
  };

  const handleUrlChange = (userType) => {
    if (action) {
      const newUrl = `/admin-dashboard/${userType}/${action}`;
      window.history.pushState({}, "", newUrl); 
    }
  };

  const resetUrl = () => {
    window.history.pushState({}, "", "/admin-dashboard"); // Reset URL to base path
  };

  // Fetch data when action is "change" and userType is selected
  useEffect(() => {
    if (action === "change" && userType) {
      const url = `${API_CONFIG.REST_BASE_URL}/api/${userType}`;
      const token = localStorage.getItem("authToken"); 
  
      axios
        .get(url, {
          headers: {
            Authorization: `Bearer ${token}`, 
          },
        })
        .then((response) => {
          setData(response.data); 
          setError(""); // Clear error
        })
        .catch((err) => {
          console.error("Error fetching data:", err);
          setError("Failed to fetch data. Please try again.");
          setData([]); 
        });
    }
  }, [action, userType]);

  return (
    <div>
      <div className="buttons">
        <button onClick={() => toggleBox("add")}>Add User</button>
        <button onClick={() => toggleBox("change")}>Change User</button>
        <button onClick={() => toggleBox("delete")}>Delete User</button>
      </div>
      <div className={`selectUserType ${visible}`}>
        <button onClick={() => { toggleSecondBox("students"); handleUrlChange("students"); }}>Students</button>
        <button onClick={() => { toggleSecondBox("professors"); handleUrlChange("professors"); }}>Professors</button>
      </div>

      <div className={`selectUserType ${visible2}`}>
        {action === "add" && userType === "students" ? (
          <div className="content">
            <h3>Add Student</h3>
            <form>
              <div>
                <label htmlFor="nume">Nume</label>
                <input type="text" name="nume" required />
              </div>

              <div>
                <label htmlFor="prenume">Prenume</label>
                <input type="text" name="prenume" required />
              </div>

              <div>
                <label htmlFor="email">Email</label>
                <input type="email" name="email" required />
              </div>

              <div>
                <label htmlFor="cicluStudii">Ciclu Studii</label>
                <input type="text" name="cicluStudii" required />
              </div>

              <div>
                <label htmlFor="grupa">Grupa</label>
                <input type="text" name="grupa" required />
              </div>

              <div>
                <label htmlFor="anStudiu">An Studii</label>
                <input type="number" name="anStudiu" required />
              </div>
              <div className="btns">
                <button type="submit">Add</button>
                <button type="submit">Cancel</button>
              </div>
            </form>
          </div>
        ) : action === "add" && userType === "professors" ? (
          <div className="content">
            <h3>Add Professor</h3>
            <form>
              <div>
                <label htmlFor="nume">Nume</label>
                <input type="text" name="nume" required />
              </div>

              <div>
                <label htmlFor="prenume">Prenume</label>
                <input type="text" name="prenume" required />
              </div>

              <div>
                <label htmlFor="email">Email</label>
                <input type="email" name="email" required />
              </div>

              <div>
                <label htmlFor="gradDidactic">Grad Didactic</label>
                <input type="text" name="gradDidactic" required />
              </div>

              <div>
                <label htmlFor="tipAsociere">Tip Asociere</label>
                <input type="text" name="tipAsociere" required />
              </div>

              <div>
                <label htmlFor="afiliere">Afiliere</label>
                <input type="text" name="afiliere" required />
              </div>
              <div className="btns">
                <button type="submit">Add</button>
                <button type="submit">Cancel</button>
              </div>
            </form>

          </div>
        ) : action === "change" && userType ? (
          <div className="content">
            <h3>Change {userType === "students" ? "Students" : "Professors"}</h3>
            {error && <p className="error">{error}</p>}
            {data.length > 0 && (
              <div>
              {data.map((item, index) => (
                <button
                  key={index}
                  className="users"
                  onClick={() => toggleThirdBox(item)}
                >
                  {userType === "students"
                    ? `${item.nume} ${item.prenume} (${item.email})`
                    : `${item.nume} ${item.prenume} - ${item.grad_didactic}`}
                </button>
              ))}
            </div>
            )}
          </div>
        ) : (
          <div>
            <p>Please select an action and user type to proceed.</p>
          </div>
        )}
      </div>
      <div className={`selectUserType ${visible3}`}>
        {selectedUser && (
          <form className="changeUser">
            <div>
              <label htmlFor="nume">Nume</label>
              <input
                type="text"
                name="nume"
                value={selectedUser.nume}
                required
              />
            </div>

            <div>
              <label htmlFor="prenume">Prenume</label>
              <input
                type="text"
                name="prenume"
                value={selectedUser.prenume}
                placeholder={selectedUser.prenume}
                required
              />
            </div>

            <div>
              <label htmlFor="email">Email</label>
              <input
                type="email"
                name="email"
                value={selectedUser.email}
                required
              />
            </div>

            {userType === "students" && (
              <>
                <div>
                  <label htmlFor="cicluStudii">Ciclu Studii</label>
                  <input
                    type="text"
                    name="cicluStudii"
                    value={selectedUser.cicluStudii}
                    required
                  />
                </div>
                <div>
                  <label htmlFor="anStudiu">An Studii</label>
                  <input
                    type="number"
                    name="anStudiu"
                    value={selectedUser.anStudiu}
                    required
                  />
                </div>
                <div>
                  <label htmlFor="grupa">Grupa</label>
                  <input
                    type="text"
                    name="grupa"
                    value={selectedUser.grupa}
                    required
                  />
                </div>
              </>
            )}

            {userType === "professors" && (
              <>
                <div>
                  <label htmlFor="gradDidactic">Grad Didactic</label>
                  <input
                    type="text"
                    name="gradDidactic"
                    value={selectedUser.gradDidactic}
                    required
                  />
                </div>
                <div>
                  <label htmlFor="tipAsociere">Tip Asociere</label>
                  <input
                    type="text"
                    name="tipAsociere"
                    value={selectedUser.tipAsociere}
                    required
                  />
                </div>
                <div>
                  <label htmlFor="afiliere">Afiliere</label>
                  <input
                    type="text"
                    name="afiliere"
                    value={selectedUser.afiliere}
                    required
                  />
                </div>
              </>
            )}

            <div className="btns">
              <button type="submit">Save Changes</button>
              <button type="button" onClick={() => setIsOpen3(false)}>
                Cancel
              </button>
            </div>
          </form>
        )}
      </div>
    </div>
  );
}

export default AdminDashboard;
